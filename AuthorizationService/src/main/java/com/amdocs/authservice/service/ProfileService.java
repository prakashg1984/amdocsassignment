package com.amdocs.authservice.service;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.amdocs.authservice.bo.Profile;
import com.amdocs.authservice.util.AssignmentUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ProfileService {
	private static final Logger log = LoggerFactory.getLogger(ProfileService.class);

	@Value("${profile.service.url}")
	public String profileServiceURL;
	
	RestTemplate restTemplate;
	
	@Autowired
	RestTemplateBuilder builder;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	AssignmentUtil assignmentUtil;
	
	@Resource(name = "RouteEventProducerTemplate")
	private KafkaTemplate<String, Map> sender;
	
	@Value("${profile.kafka.topic:PROFILE_TOPIC}")
	private String topic;
	
	public static final String DELETE_EVENT = "DeleteProfile";
	public static final String UPDATE_EVENT ="UpdatePofile";

	/*
	 * @Autowired public ProfileService(RestTemplateBuilder builder) {
	 * this.restTemplate = builder.build(); }
	 */
	
	@PostConstruct
	public void init() throws Exception {
		//restTemplate = builder.basicAuthentication("assignment_user", "assignment_pwd").build();
		restTemplate = builder.build();
	}
	
	/**
	 * Method to Get the UserProfile by invoking the Profile Service
	 * @param userName
	 * @return
	 */
	public Profile getProfile(String userName) {
		Profile profile = new Profile();
		try {
			String getProfileUrl = profileServiceURL + "{name}";
			Map<String, String> input = new HashMap<>();
			input.put("name", userName);
			
			//Invoke the Profile Service GET API by passing UserName
			ResponseEntity<String> response = restTemplate.exchange(getProfileUrl,
					HttpMethod.GET, null, String.class, input);

			log.info("Response from PROFILE_SERVICE {} : CODE {} : ",response,response.getStatusCode());
			if(response.getStatusCode() == HttpStatus.OK) {
				log.info("Response Body from PROFILE_SERVICE {} ",response.getBody());
				profile = objectMapper.readValue(response.getBody(), Profile.class);
				return profile;
			}
		}catch(Exception e) {
			e.printStackTrace();
			log.error("Error while invoking AUTH_SERVICE {} ",e.getMessage());
		}
		return profile;
	}
	
	/**
	 * Method to Add the UserProfile by invoking the Profile Service
	 * @param profile
	 * @return
	 */
	public boolean addProfile(Profile profile) {
		
		HttpEntity<Profile> requestBody = new HttpEntity<>(profile);
		try {
			//Invoke the Profile Service POST API by passing the Profile Object
			ResponseEntity<String> response = restTemplate.exchange(profileServiceURL,
					HttpMethod.POST, requestBody, String.class);

			log.info("Response from PROFILE_SERVICE {} : CODE {} : ",response,response.getStatusCode());
			if(response.getStatusCode() == HttpStatus.OK) {
				return true;
			}
		}catch(Exception e) {
			e.printStackTrace();
			log.error("Error while invoking PROFILE_SERVICE {} ",e.getMessage());
		}
		return false;
	}
	
	/**
	 * Method to Update the UserProfile by invoking the Profile Service through Async Kafka
	 * @param profile
	 * @return
	 */
	public boolean updateProfile(Profile profile) {
		//Create the Event Request for Profile Update
		Map profileEvent = assignmentUtil.createEvent(profile, UPDATE_EVENT);
		log.info("Profile Event for UpdateProfile {} ",profileEvent);
		try {
			//Send the Event to Kafka Topic
			sender.send(topic,profile.getUserName() ,profileEvent);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return true;
	}
	
	/**
	 * Method to Delete the UserProfile by invoking the Profile Service through Async Kafka
	 * @param profile
	 * @return
	 */
	public Profile deleteProfile(Profile profile) {
		//Create the Event Request for Profile Delete
		Map profileEvent = assignmentUtil.createEvent(profile, DELETE_EVENT);
		log.info("Profile Event for DeleteProfile {} ",profileEvent);
		try {
			//Send the Event to Kafka Topic
			sender.send(topic,profile.getUserName() ,profileEvent);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return profile;
	}
}

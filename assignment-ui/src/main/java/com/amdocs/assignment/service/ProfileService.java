package com.amdocs.assignment.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.amdocs.assignment.bo.Profile;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class ProfileService {

	private static final Logger log = LoggerFactory.getLogger(ProfileService.class);

	@Value("${auth.service.url}")
	public String authServiceURL;
	
	RestTemplate restTemplate;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	public ProfileService(RestTemplateBuilder builder) {
	    this.restTemplate = builder.build();
	}
	
	public static final String DELETE_EVENT = "DeleteProfile";
	public static final String UPDATE_EVENT ="UpdatePofile";
	
	/**
	 * Method to get the User Profile for a given UserName by invoking the proxy Authorization Service
	 * @param userName
	 * @return
	 */
	public Profile getProfile(String userName) {
		Profile profile = new Profile();
		try {
			
			String getProfileUrl = authServiceURL + "profile/" + "{name}";
			Map<String, String> input = new HashMap<>();
			input.put("name", userName);
			//Invoke the Proxy URL in AuthorizationService to get UserProfile by passing userName
			ResponseEntity<String> response = restTemplate.exchange(getProfileUrl,
					HttpMethod.GET, null, String.class, input);

			log.info("Response from AUTH_SERVICE {} : CODE {} : ",response,response.getStatusCode());
			if(response.getStatusCode() == HttpStatus.OK) {
				log.info("Response Body from AUTH_SERVICE {} ",response.getBody());
				profile = objectMapper.readValue(response.getBody(), Profile.class);
			}
		}catch(Exception e) {
			log.error("Error while invoking AUTH_SERVICE {} ",e.getMessage());
		}
		return profile;
	}
	
	/**
	 * Method to Add the User Profile by invoking the proxy Authorization Service
	 * @param profile
	 * @return
	 */
	public Profile addProfile(Profile profile) {
		log.info("addProfile {} ",profile);
		HttpEntity<Profile> requestBody = new HttpEntity<>(profile);
		try {
			//Invoke the Proxy URL in AuthorizationService to Add UserProfile
			ResponseEntity<String> response = restTemplate.exchange(authServiceURL + "profile" ,
					HttpMethod.POST, requestBody, String.class);

			log.info("Response from AUTH_SERVICE {} : CODE {} : ",response,response.getStatusCode());
			if(response.getStatusCode() == HttpStatus.OK) {
				return profile;
			}
		}catch(Exception e) {
			log.error("Error while invoking AUTH_SERVICE {} ",e.getMessage());
		}
		return profile;
	}
	
	/**
	 * Method to Update the User Profile by invoking the proxy Authorization Service
	 * @param profile
	 * @return
	 */
	public Profile updateProfile(Profile profile) {
		
		HttpEntity<Profile> requestBody = new HttpEntity<>(profile);
		try {
			//Invoke the Proxy URL in AuthorizationService to Update UserProfile
			ResponseEntity<String> response = restTemplate.exchange(authServiceURL + "profile" , HttpMethod.PUT, requestBody,
					String.class);

			log.info("Response from AUTH_SERVICE {} : CODE {} : ", response, response.getStatusCode());
			if (response.getStatusCode() == HttpStatus.OK) {
				return profile;
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return profile;
	}
	
	/**
	 * Method to Delete the User Profile by invoking the proxy Authorization Service
	 * @param profile
	 * @return
	 */
	public Profile deleteProfile(Profile profile) {
		
		String getProfileUrl = authServiceURL + "profile/"  + "{name}";
		Map<String, String> input = new HashMap<>();
		input.put("name", profile.getUserName());
		
		//Invoke the Proxy URL in AuthorizationService to Delete UserProfile
		ResponseEntity<String> response = restTemplate.exchange(getProfileUrl,
				HttpMethod.DELETE, null, String.class, input);

		log.info("Response from AUTH_SERVICE {} : CODE {} : ",response,response.getStatusCode());
		
		return profile;
	}
}

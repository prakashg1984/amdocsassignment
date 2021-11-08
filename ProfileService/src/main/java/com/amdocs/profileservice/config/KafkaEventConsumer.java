package com.amdocs.profileservice.config;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.amdocs.profileservice.bo.Profile;
import com.amdocs.profileservice.service.ProfileService;
import com.amdocs.profileservice.util.ProfileServiceConstants;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.micrometer.core.instrument.util.StringUtils;

@Component
@EnableKafka
public class KafkaEventConsumer {

    static Logger logger = LoggerFactory.getLogger(KafkaEventConsumer.class);

    @Autowired
    ObjectMapper objectMapper;
    
    @Autowired
    ProfileService profileService;
    
    /**
     * Kafka Listener to consume the Event request from kafka topic
     * @param profileEventRequest
     */
	@KafkaListener(topics = "${profile.kafka.topic:PROFILE_TOPIC}", containerFactory = "kafkaListenerContainerFactory")
	public void receiveEvent(String profileEventRequest) {
		logger.info("Received profileEventRequest "+profileEventRequest);
		
		try {
			//Convert the incoming Event request to Profile Object
			Map<String, Object> profileEventMap = objectMapper.readValue(profileEventRequest, Map.class);
			Profile profile = objectMapper.convertValue((Map)profileEventMap.get("data"), Profile.class);
			String eventType = profileEventMap.get("eventType").toString();
			logger.info("eventType {} : profile {} ",eventType,profile);
			
			if(null != eventType && null != profile && StringUtils.isNotBlank(profile.getUserName())) {
				//Based on the Event Type invoke Update or Delete Profile
				if(eventType.equalsIgnoreCase(ProfileServiceConstants.UPDATE_EVENT)) {
					profileService.updateProfile(profile);
				}else if(eventType.equalsIgnoreCase(ProfileServiceConstants.DELETE_EVENT)) {
					profileService.deleteProfile(profile);
				}else {
					logger.warn("Invalid Event {} ",eventType);
				}
			}else {
				logger.warn("Invalid Message {} ",profileEventRequest);
			}
		} catch (Exception e) {
			logger.error("Error in Json Conversion {} ",e.getMessage());
			e.printStackTrace();
		} 
	}
}

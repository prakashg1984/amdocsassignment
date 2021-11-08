package com.amdocs.authservice.util;

import org.springframework.stereotype.Component

import com.amdocs.authservice.bo.Profile

@Component
class AssignmentUtil {
	
	/**
	 * Method to Create the Event Payload for Profile Update and Delete
	 * @param profile
	 * @param eventType
	 * @return
	 */
	Map createEvent(Profile profile, String eventType) {
		Map profileEvent = new HashMap();
		
		profileEvent.eventId = UUID.randomUUID()
		profileEvent.eventType = eventType
		profileEvent.data = profile
		
		return profileEvent;
	}
}

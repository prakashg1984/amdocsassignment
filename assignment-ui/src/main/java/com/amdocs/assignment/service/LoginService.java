package com.amdocs.assignment.service;

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

import com.amdocs.assignment.bo.User;


@Service
public class LoginService {

	private static final Logger log = LoggerFactory.getLogger(LoginService.class);

	@Value("${auth.service.url}")
	public String authServiceURL;
	
	RestTemplate restTemplate;
	
	@Autowired
	public LoginService(RestTemplateBuilder builder) {
	    this.restTemplate = builder.build();
	}
	
	/**
	 * Method to validate the User Credentials by invoking the AuthorizationService API
	 * @param user
	 * @return
	 */
	public boolean validateUser(User user) {

		HttpEntity<User> requestBody = new HttpEntity<>(user);

		try {
			//Invoke the AuthorizationService REST API by passing user credentials
			ResponseEntity<String> response = restTemplate.exchange(authServiceURL + "login",
					HttpMethod.POST, requestBody, String.class);

			log.info("Response from AUTH_SERVICE {} : CODE {} : ",response,response.getStatusCode());
			if(response.getStatusCode() == HttpStatus.OK) {
				return true;
			}
		}catch(Exception e) {
			log.error("Error while invoking AUTH_SERVICE {} ",e.getMessage());
		}
		return false;
	}
}

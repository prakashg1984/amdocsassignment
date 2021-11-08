package com.amdocs.authservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.amdocs.authservice.bo.User;
import com.amdocs.authservice.service.AuthorizationService;

@RestController
@RequestMapping("")
public class AuthorizationController {

	private static final Logger log = LoggerFactory.getLogger(AuthorizationController.class);

	@Autowired
	AuthorizationService authorizationService;

	/**
	 * Method to authorize the User Credentials and return Success or Failure for Authorization
	 * @param user
	 * @return
	 */
	@PostMapping("/login")
	@ResponseBody
	public ResponseEntity<Object> authorizeUser(@RequestBody User user) {
		boolean validUser = authorizationService.validateUser(user);
		log.info("User {} is authorized {} ",user.getUserName(),validUser);
		if(validUser) {
			return ResponseEntity.status(HttpStatus.OK).body(validUser);
		}else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(validUser);
		}
		
	}
	
}

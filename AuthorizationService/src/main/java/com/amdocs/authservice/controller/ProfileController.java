package com.amdocs.authservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.amdocs.authservice.bo.Profile;
import com.amdocs.authservice.service.ProfileService;

@RestController
@RequestMapping("")
public class ProfileController {

	private static final Logger log = LoggerFactory.getLogger(ProfileController.class);

	@Autowired
	ProfileService profileService;
	
	/**
	 * Method to Add the User Profile
	 * @param profile
	 * @return
	 */
	@RequestMapping(value = "/profile", method = RequestMethod.POST)
	@ResponseBody
	public HttpStatus addProfile(@RequestBody Profile profile) {
		log.info("Add Profile {} ",profile);
		return profileService.addProfile(profile) ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
	}
	
	/**
	 * Method to Update the User Profile
	 * @param profile
	 * @return
	 */
	@RequestMapping(value = "/profile", method = RequestMethod.PUT)
	@ResponseBody
	public HttpStatus updateProfile(@RequestBody Profile profile) {
		return profileService.updateProfile(profile) ? HttpStatus.ACCEPTED : HttpStatus.BAD_REQUEST;
	}
	
	/**
	 * Method to Delete the User Profile based on given UserName
	 * @param userName
	 * @return
	 */
	@RequestMapping(value = "/profile/{userName}", method = RequestMethod.DELETE)
	@ResponseBody
	public HttpStatus deleteProfile(@PathVariable String userName) {
		Profile users = profileService.getProfile(userName);
		if(null != users) {
			profileService.deleteProfile(users);
			return HttpStatus.OK;
		}
		return HttpStatus.NO_CONTENT;
	}
	
	/**
	 * Method to Get the User Profile based on given UserName
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/profile/{name}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Object> getUserProfile(@PathVariable String name) {
		Profile userProfile = profileService.getProfile(name);
		log.info("User Profile : {} ",userProfile);
		if(null != userProfile) {
			return ResponseEntity.status(HttpStatus.OK).body(userProfile);
		}else {
			return ResponseEntity.status(HttpStatus.OK).body(null);
		}
	}
	
}

package com.amdocs.profileservice.controller;

import java.util.List;

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

import com.amdocs.profileservice.bo.Profile;
import com.amdocs.profileservice.service.ProfileService;

@RestController
@RequestMapping("")
public class ProfileController {

	private static final Logger log = LoggerFactory.getLogger(ProfileController.class);

	@Autowired
	ProfileService profileService;
	
	/**
	 * Method to Get the UserProfile for a given UserName
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/profile/{name}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Object> getUserProfile(@PathVariable String name) {
		List<Profile> userProfile = profileService.getUserProfile(name);
		log.info("User Profile : {} ",userProfile);
		if(null != userProfile && userProfile.size() > 0) {
			return ResponseEntity.status(HttpStatus.OK).body(userProfile.get(0));
		}else {
			return ResponseEntity.status(HttpStatus.OK).body(null);
		}
	}
	
	/**
	 * Method to Add the UserProfile
	 * @param profile
	 * @return
	 */
	@RequestMapping(value = "/profile", method = RequestMethod.POST)
	@ResponseBody
	public HttpStatus addProfile(@RequestBody Profile profile) {
		return profileService.addProfile(profile) ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
	}
	
	/**
	 * Method to Update the UserProfile
	 * @param profile
	 * @return
	 */
	@RequestMapping(value = "/profile", method = RequestMethod.PUT)
	@ResponseBody
	public HttpStatus updateProfile(@RequestBody Profile profile) {
		return profileService.updateProfile(profile) ? HttpStatus.ACCEPTED : HttpStatus.BAD_REQUEST;
	}
	
	/**
	 * Method to Delete the UserProfile based on given UserName
	 * @param userName
	 * @return
	 */
	@RequestMapping(value = "/profile/{userName}", method = RequestMethod.DELETE)
	@ResponseBody
	public HttpStatus deleteProfile(@PathVariable String userName) {
		List<Profile> users = profileService.getUserProfile(userName);
		if(null != users && users.size() > 0) {
			profileService.deleteProfile(users.get(0));
			return HttpStatus.OK;
		}
		return HttpStatus.NO_CONTENT;
	}
	
}

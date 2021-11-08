package com.amdocs.assignment.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.amdocs.assignment.bo.Profile;
import com.amdocs.assignment.bo.User;
import com.amdocs.assignment.service.ProfileService;

@Controller
public class ProfileController {

	private static final Logger log = LoggerFactory.getLogger(ProfileController.class);

	@Autowired
	ProfileService profileService;
	
	/**
	 * Method to ADD Profile for a logged in User. Will be invoked by Profile.html
	 * @param profile
	 * @param bindingResult
	 * @param model
	 * @param session
	 * @return
	 */
	@PostMapping("/addProfile")
	public String addProfile(@ModelAttribute @Valid Profile profile, BindingResult bindingResult,Model model, HttpSession session) {
	
		log.info("profile : {} ",profile.toString());
		profile.setUserName(session.getAttribute("userName").toString());
		
		profileService.addProfile(profile);
		
		model.addAttribute("profile", profile);
		model.addAttribute("updateFlow", true);
		model.addAttribute("message", "Logged In UserName "+session.getAttribute("userName"));
		model.addAttribute("profileMessage", "Profile Added Successfully for UserName "+session.getAttribute("userName"));
		
		log.info("userName : {} ",session.getAttribute("userName"));

		return "profile";
	}
	
	/**
	 * Method to delete the profile of the logged in User. Will be invoked by Profile.html
	 * @param profile
	 * @param bindingResult
	 * @param model
	 * @param session
	 * @return
	 */
	@PostMapping("/deleteProfile")
	public String deleteProfile(@ModelAttribute @Valid Profile profile, BindingResult bindingResult,Model model, HttpSession session) {
		
		log.info("profile : {} ",profile.toString());
		profile.setUserName(session.getAttribute("userName").toString());
		
		profileService.deleteProfile(profile);
		
		model.addAttribute("message", "Profile "+session.getAttribute("userName") +" deleted");
		model.addAttribute("user", new User());
		session.invalidate();
		return "login";
	}
	
	/**
	 * Method to Update the Profile of Logged in User. Will be invoked by Profile.html
	 * @param profile
	 * @param bindingResult
	 * @param model
	 * @param session
	 * @return
	 */
	@PostMapping("/updateProfile")
	public String updateProfile(@ModelAttribute @Valid Profile profile, BindingResult bindingResult,Model model, HttpSession session) {
	
		log.info("profile : {} ",profile.toString());
		profile.setUserName(session.getAttribute("userName").toString());
		
		profileService.updateProfile(profile);
		
		model.addAttribute("profile", profile);
		model.addAttribute("updateFlow", true);
		model.addAttribute("message", "Logged In UserName "+session.getAttribute("userName"));
		model.addAttribute("profileMessage", "Profile Updated Successfully for UserName "+session.getAttribute("userName"));
		
		log.info("userName : {} ",session.getAttribute("userName"));

		return "profile";
	}
	
}

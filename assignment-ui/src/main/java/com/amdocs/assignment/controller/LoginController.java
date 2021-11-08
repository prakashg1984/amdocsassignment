package com.amdocs.assignment.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.amdocs.assignment.bo.Profile;
import com.amdocs.assignment.bo.User;
import com.amdocs.assignment.service.LoginService;
import com.amdocs.assignment.service.ProfileService;

import io.micrometer.core.instrument.util.StringUtils;

@Controller
public class LoginController {

	private static final Logger log = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	LoginService loginService;
	
	@Autowired
	ProfileService profileService;
	
	@Value("${errormessage}")
	public String errormessage;
	
	/**
	 * Method to Load the login.html page
	 * @param model
	 * @param session
	 * @return
	 */
	@GetMapping("/login")
	public String login(Model model,HttpSession session) {
		model.addAttribute("user", new User());
		log.trace("Inside LoginController");
		return "login";
	}
	
	/**
	 * Method to validate the User on click of SignIn button from login.html
	 * @param user
	 * @param bindingResult
	 * @param model
	 * @param session
	 * @return
	 */
	@PostMapping("/login")
	public String loginUser(@ModelAttribute @Valid User user, BindingResult bindingResult,Model model, HttpSession session) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("errormessage", errormessage);
			return "login";
		}
		
		if(loginService.validateUser(user)) {
			Profile profile = profileService.getProfile(user.getUserName());
			model.addAttribute("profile", profile);
			if(null != profile && StringUtils.isNotBlank(profile.getUserName())) {
				model.addAttribute("updateFlow", true);
			}
			
			session.setAttribute("userName", user.getUserName());
			model.addAttribute("message", "Logged In UserName "+user.getUserName());
			return "profile";
		}else {
			model.addAttribute("message", "Invalid Credentials");
			return "login";
		}
	}
	
	/**
	 * Method t logout the User and invalidate the session
	 * @param user
	 * @param model
	 * @param session
	 * @return
	 */
	@GetMapping("/logout")
	public String logout(@ModelAttribute User user,Model model,HttpSession session) {
		model.addAttribute("message", "User "+session.getAttribute("userName") +" logged out");
		session.invalidate();
		return "login";
	}
}

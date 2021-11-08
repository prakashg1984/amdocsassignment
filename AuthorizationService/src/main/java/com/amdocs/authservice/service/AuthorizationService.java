package com.amdocs.authservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amdocs.authservice.bo.User;
import com.amdocs.authservice.dao.UserRepository;

@Service
public class AuthorizationService {
	
	@Autowired
	UserRepository userRepository;
	
	/**
	 * Validate the User Credentials by invoking the CRUD repository
	 * @param user
	 * @return
	 */
	public boolean validateUser(User user) {
		List<User> users = userRepository.findByUserNameAndPassword(user.getUserName(),user.getPassword());
		return (null != users && users.size() > 0) ? true : false;
	}
}

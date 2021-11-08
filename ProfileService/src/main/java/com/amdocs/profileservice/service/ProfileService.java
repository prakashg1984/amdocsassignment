package com.amdocs.profileservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amdocs.profileservice.bo.Profile;
import com.amdocs.profileservice.dao.ProfileRepository;

@Service
public class ProfileService {
	
	@Autowired
	ProfileRepository profileRepository;
	
	/**
	 * Method to Get the UserProfile based on given UserName
	 * @param userName
	 * @return
	 */
	public List<Profile> getUserProfile(String userName) {
		List<Profile> users = profileRepository.findByUserName(userName);
		return users;
	}
	
	/**
	 * Method to Add the UserProfile by invoking the CRUD Repository
	 * @param profile
	 * @return
	 */
	@Transactional
	public boolean addProfile(Profile profile) {
		return profileRepository.save(profile) != null;
	}
	
	/**
	 * Method to Update the UserProfile by invoking the CRUD Repository
	 * @param profile
	 * @return
	 */
	@Transactional
	public boolean updateProfile(Profile profile) {
		return profileRepository.save(profile) != null;
	}
	
	/**
	 * Method to Delete the UserProfile by invoking the CRUD Repository
	 * @param profile
	 */
	@Transactional
	public void deleteProfile(Profile profile) {
		profileRepository.delete(profile);
	}
}

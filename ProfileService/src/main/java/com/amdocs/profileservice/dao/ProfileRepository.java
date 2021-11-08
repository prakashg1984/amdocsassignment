package com.amdocs.profileservice.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.amdocs.profileservice.bo.Profile;

@Repository
public interface ProfileRepository extends CrudRepository<Profile, String>  {
	List<Profile> findByUserName(String userName);
    
}


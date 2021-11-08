package com.amdocs.authservice.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.amdocs.authservice.bo.User;

@Repository
public interface UserRepository extends CrudRepository<User, String>  {
	List<User> findByUserNameAndPassword(String userName,String password);
    
}


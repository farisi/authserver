package com.sf.sso.app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sf.sso.app.entities.User;
import com.sf.sso.app.repositories.UserRepository;

@Service
public class UserService {
	
	private UserRepository userRepo;

	public UserService(UserRepository userRepo) {
		super();
		this.userRepo = userRepo;
	}
	
	public List<User> all(){
		return userRepo.findAll();
	}
	
	public User findByEmail(String email) {
		Optional<User> useropt=userRepo.findByEmail(email);
		return useropt.get();
	}
}
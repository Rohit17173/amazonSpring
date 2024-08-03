package com.example.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.model.LoginReturn;
import com.example.project.model.User;
import com.example.project.repo.UserRepo;

@RestController
@CrossOrigin
@RequestMapping("login")
class LoginController {
	
	@Autowired
	UserRepo userRepo;
	
	@RequestMapping("hello")
	public String hello() {
		return "hello";
	}
	
	@RequestMapping("name{id}")
	public String[] getname(@PathVariable int id) {
		User user =userRepo.findById(id).get();
		String sa[]=new String[1];
		sa[0]=user.getName();
		return sa;
	}
	
	@RequestMapping("log")
	public LoginReturn login(@RequestBody String[] str) {
		if(str==null) {
			return new LoginReturn(-1, -1, "data Corrupted");
		}
		String username=str[0];
		if (username==null || username.length()<1) {
			return new LoginReturn(-1, -1, "Enter username");
		}
		String password=str[1];
		if (password==null || password.length()<1) {
			return new LoginReturn(-1, -1, "Enter password");
		}
		
		int count = userRepo.countByUsername(username);
		
		if (count==0) {
			return new LoginReturn(-1, -1, "username not found");
		}
		
		if (count>1) {
			return new LoginReturn(-1, -1, "multiple usernames please contanct admin");
		}
		
		User user=userRepo.findByUsername(username);
		
		if(user.getPassword().equals(password)) {
			return new LoginReturn(user.getId(), user.getAccountType(), null);
		}else {
			return new LoginReturn(-1, -1, "password wrong");
		}
	}

	@RequestMapping("register")
	public int login(@RequestBody User user) {
		
		if(user== null) {
			return 0;
		}
		String username=user.getUsername();
		if (username==null || username.length()<1) {
			return 1;
		}
		String password=user.getPassword();
		if (password==null || password.length()<1) {
			return 2;
		}
		
		int count = userRepo.countByUsername(username);
		
		if (count==0) {
			userRepo.save(user);
			return 4;
		}else {
			return  3;
		}

	}

}

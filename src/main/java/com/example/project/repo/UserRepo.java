package com.example.project.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project.model.User;

public interface UserRepo extends JpaRepository<User, Integer>{
	
	public int countByUsername(String username);
	
	public User findByUsername(String username);
	
	public List<User> findByAccountType(int a) ;
	

}

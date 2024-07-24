package com.example.project.controller;

import java.sql.Date;
import java.util.List;
import java.util.Locale.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.model.Cateogory;
import com.example.project.model.User;
import com.example.project.repo.CateegoryRepo;
import com.example.project.repo.UserRepo;

@RestController
@CrossOrigin
@RequestMapping("admin")
public class AdminController {

	@Autowired
	CateegoryRepo repo;
	
	@RequestMapping("getAll")
	public List<Cateogory> GetAll(){
		return repo.findAll();
	}
	
	@RequestMapping("addCat{id}")
	public Cateogory addCategory(@PathVariable int id,@RequestBody String name){
		Cateogory c=new Cateogory();
		c.setDate(new java.util.Date());
		c.setName(name);
		c.setUserid(id);
		
		return repo.save(c);
	}
	
	
}

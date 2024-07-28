package com.example.project.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project.model.MyOrder;

public interface MyOrderRepo extends JpaRepository<MyOrder, Integer>{
	
	public List<MyOrder> findByUserid(int userid);

}

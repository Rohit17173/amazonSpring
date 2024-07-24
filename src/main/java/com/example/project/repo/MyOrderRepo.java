package com.example.project.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project.model.MyOrder;

public interface MyOrderRepo extends JpaRepository<MyOrder, Integer>{

}

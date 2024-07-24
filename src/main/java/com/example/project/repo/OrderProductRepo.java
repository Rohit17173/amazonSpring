package com.example.project.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project.model.OrderProduct;

public interface OrderProductRepo extends JpaRepository<OrderProduct, Integer>{

}

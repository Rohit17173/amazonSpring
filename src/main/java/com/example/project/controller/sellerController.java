package com.example.project.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.model.Product;
import com.example.project.projection.ProductUiSeller;
import com.example.project.repo.ProductRepo;

@RestController
@CrossOrigin
@RequestMapping("seller")
public class sellerController {
	
	@Autowired
	ProductRepo productRepo;
	
	@RequestMapping("getAllProd/{id}")
	public List<ProductUiSeller> getall(@PathVariable int id){
		System.out.println(id);
//		return productRepo.findByUserid(id);
		return productRepo.findProductByUserId(id);
	}
	
	@RequestMapping("AddProd")
	public Product Addproduct(@RequestBody Product product) {
		System.out.println(product);
		product.setDate(new Date());
		return productRepo.save(product);
	}
}

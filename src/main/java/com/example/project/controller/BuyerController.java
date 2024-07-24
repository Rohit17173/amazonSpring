package com.example.project.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.model.Cart;
import com.example.project.model.MyOrder;
import com.example.project.model.OrderProduct;
import com.example.project.model.Product;
import com.example.project.projection.CartProduct;
import com.example.project.projection.productUiBuyer;
import com.example.project.repo.CartRepo;
import com.example.project.repo.MyOrderRepo;
import com.example.project.repo.OrderProductRepo;
import com.example.project.repo.ProductRepo;
//import com.example.project.projection.productUiBuyer;

@RestController
@CrossOrigin
@RequestMapping("buyer")
public class BuyerController {

	@Autowired
	ProductRepo productRepo;
	
	@Autowired
	CartRepo cartRepo;
	
	@Autowired
	MyOrderRepo myorderRepo; 
	
	@Autowired
	OrderProductRepo orderProductRepo;
	
	@RequestMapping("getProductByFilter")
	public List<productUiBuyer> getProductByFilter(@RequestBody int[] a){
		return productRepo.getProductByFilter(a[0], a[1], a[2], a[3]);
	}
	
	@RequestMapping("addToCart/{pid}/{uid}")
	public int addCartProduct(@PathVariable int pid,@PathVariable int uid){
		
		int count=cartRepo.countByUseridAndProductid(uid, pid);
		if(count==0) {
			Cart ca=new Cart();
			ca.setProductid(pid);
			ca.setUserid(uid);
			cartRepo.save(ca);
			return 1;
			
		}else {
			return 0;
		}
	}
	
	@RequestMapping("getcart/{id}")
	public List<CartProduct> getCartProduct(@PathVariable int id) {
		return cartRepo.findCartProductByUserId(id);
	}
	
	@RequestMapping("placeOrder/{id}")
	public int placeOrder(@PathVariable int id,@RequestBody int[][] a)
	{
		try {
			MyOrder order=new MyOrder();
			order.setDate(new Date());
			order.setUserid(id);
			myorderRepo.save(order);
			double totalAmount=0;
			for (int i = 0; i < a.length; i++) {
				int[] a1=a[i];
				int catrid=a1[0];
				int quantity=a1[1];
				Cart cart=cartRepo.findById(catrid).get();
				int productid=cart.getProductid();
				Product product=productRepo.findById(productid).get();
				OrderProduct obj=new OrderProduct();
				
				double amount=product.getPrice()*quantity;
				amount = amount -(amount*product.getDiscount()/100);
				obj.setAmount(amount);
				totalAmount+=amount;
				
				obj.setDate(new Date());
				obj.setProductid(productid);
				
				orderProductRepo.save(obj);
				
				cartRepo.delete(cart);
			}
			order.setAmount(totalAmount);
			myorderRepo.save(order);
			return 1;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
	}
	
}
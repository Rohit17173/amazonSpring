package com.example.project.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.model.Cart;
import com.example.project.model.MyOrder;
import com.example.project.model.OrderProduct;
import com.example.project.model.Product;
import com.example.project.model.Ratings;
import com.example.project.projection.BuyerHistory;
import com.example.project.projection.CartProduct;
import com.example.project.projection.productUiBuyer;
import com.example.project.repo.CartRepo;
import com.example.project.repo.MyOrderRepo;
import com.example.project.repo.OrderProductRepo;
import com.example.project.repo.ProductRepo;
//import com.example.project.projection.productUiBuyer;
import com.example.project.repo.RatingRepo;

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
	
	@Autowired
	RatingRepo ratingRepo;
	
	@RequestMapping("getProductByFilter")
	public List<productUiBuyer> getProductByFilter(@RequestBody int[] a){
		return productRepo.getProductByFilter(a[0], a[1], a[2], a[3]);
	}
	
	@RequestMapping("getProduct")
	public List<Product> getProduct(){
		
		Pageable p = PageRequest.of(0, 20);
		 Page<Product> page = productRepo.findAll(p);
		 return page.getContent();
	}
	
	@RequestMapping("getSearched/{searchTerm}")
	public List<Product> getSearchedProducts(@PathVariable String searchTerm){
		int pageNo=0;
		 int pageSize = 20;
		String sortDir = Sort.Direction.ASC.name();
//		String searchTerm="acer";
		
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) 
                ? Sort.by("name").ascending()
                : Sort.by("name").descending();
		
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		
		Page<Product> page = productRepo.findByNameContaining(searchTerm, pageable);
		
		
		return page.getContent();
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
				obj.setMyorderid(order.getId());    //extra line
				obj.setQuantity(quantity);
				
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
	
	@RequestMapping("history/{id}")
	public List<BuyerHistory> historyProduct(@PathVariable int id){
		return orderProductRepo.getProductHistory(id);
	}
	
	@RequestMapping("addRating")
	public int addRating(@RequestBody int[] a){
		try {
			int userid=a[0];
			int productid=a[1];
			int stars=a[2];
			int count=ratingRepo.countByProductidAndUserid(productid, userid);
			
			System.out.println(count);
			if (count==1) {
				Ratings rev=ratingRepo.findByUseridAndProductid(userid, productid);
				rev.setStars(stars);
				rev.setDate(new Date());
				ratingRepo.save(rev);
			} else if(count==0){
				Ratings r=new Ratings();
				r.setDate(new Date());
				r.setProductid(productid);
				r.setStars(stars);
				r.setUserid(userid);
				ratingRepo.save(r);
			}else 
			{
				return 0;
			}
			double avg=ratingRepo.getAvgRatingByProductid(productid);
			Product product=productRepo.findById(productid).get();
			product.setRating(avg);
			productRepo.save(product);
			return 1;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		
		
		
		
		
	}
	
}

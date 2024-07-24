package com.example.project.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.project.model.Cart;
import com.example.project.projection.CartProduct;
import com.example.project.projection.ProductUiSeller;

public interface CartRepo extends JpaRepository<Cart, Integer>{

	public int countByUseridAndProductid(int userid,int productid);
	
	@Query(value = "SELECT c.id,p.name,p.price,p.quantity,p.description,p.discount,p.rating FROM amazon.product as p join amazon.cart as c on c.productid=p.id where c.userid= :userId", nativeQuery = true)
	List<CartProduct> findCartProductByUserId(@Param("userId") int id);

}

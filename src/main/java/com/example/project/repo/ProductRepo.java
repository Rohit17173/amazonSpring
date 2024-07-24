package com.example.project.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.project.model.Product;
import com.example.project.projection.ProductUiSeller;
import com.example.project.projection.productUiBuyer;

public interface ProductRepo extends JpaRepository<Product, Integer> {
	List<Product> findByUserid(int userid);

	@Query(value = "SELECT p.id, p.name, p.price, p.discount, p.rating, p.description, c.name as catName FROM amazon.product as p JOIN amazon.cateogory as c ON p.categoryid = c.id WHERE p.userid = :userId", nativeQuery = true)
	List<ProductUiSeller> findProductByUserId(@Param("userId") int id);

	@Query(value = "select id,name,price,rating,discount,description,quantity,datediff(now(),date) as days from amazon.product where categoryid=:i and price >=:j and price <= :k and rating<= :l", nativeQuery = true)
	List<productUiBuyer> getProductByFilter(@Param("i") int i,@Param("j") int j,@Param("k") int k,@Param("l") int l);

}

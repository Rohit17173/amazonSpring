package com.example.project.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.project.model.OrderProduct;
import com.example.project.projection.BuyerHistory;

public interface OrderProductRepo extends JpaRepository<OrderProduct, Integer>{

	@Query(value = "SELECT o.id,p.name,p.description,p.price,p.discount,o.quantity,o.amount as total,p.id as productid FROM amazon.order_product as o join amazon.my_order as m on o.myorderid=m.id join amazon.product as p on p.id=o.productid where m.userid=:userid",nativeQuery = true)
	public List<BuyerHistory> getProductHistory(@Param("userid") int userid);
}

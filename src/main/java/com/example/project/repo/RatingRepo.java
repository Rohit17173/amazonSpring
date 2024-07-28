package com.example.project.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.project.model.Ratings;

public interface RatingRepo extends JpaRepository<Ratings, Integer>{
	
	int countByProductidAndUserid(int productid,int userid);
	
	Ratings findByUseridAndProductid(int userid,int productid);
	
	@Query(value = "select avg(stars) from amazon.ratings where productid=:pid",nativeQuery = true)
	double getAvgRatingByProductid(int pid);

}

package com.driver.io.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.driver.io.entity.FoodEntity;

import javax.transaction.Transactional;


@Repository
public interface FoodRepository extends CrudRepository<FoodEntity, Long> {
	FoodEntity findByFoodId(String foodId);
	@Transactional
	@Modifying
	@Query("update foods f set f.foodId =:foodId and f.foodName =:foodName and f.foodPrice =:foodPrice and f.foodCategory =:foodCategory where f.id =:id")
	public void updateFoodEntity(long id, String foodId, String foodName, float foodPrice, String foodCategory);
}

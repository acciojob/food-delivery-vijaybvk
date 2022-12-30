package com.driver.io.repository;

import com.driver.io.entity.OrderEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface OrderRepository extends CrudRepository<OrderEntity, Long> {
	OrderEntity findByOrderId(String orderId);
	@Transactional
	@Modifying
	@Query("update orders f set f.orderId =:orderId and f.items =:items and f.cost =:cost and f.userId =:userId and f.status =:status where f.id =:id")
	public void updateOrderEntity(long id, String orderId, String[] items, float cost, String userId, boolean status);
}
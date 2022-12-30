package com.driver.io.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.driver.io.entity.UserEntity;

import javax.transaction.Transactional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {
	UserEntity findByEmail(String email);
	UserEntity findByUserId(String userId);

	@Transactional
	@Modifying
	@Query("update users f set f.userId =:userId and f.firstName =:firstName and f.lastName =:lastName and f.email =:email where f.id =:id")
	public void updateUserEntity(long id, String userId, String firstName, String lastName, String email);
}

package com.app.emedi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.emedi.bean.entity.FrontDesk;
@Repository
public interface FrontDesUserkRepo extends JpaRepository<FrontDesk, Long>{

	FrontDesk findByUsername(String username);

	FrontDesk deleteByUsername(String usernaame);

	@Query(
			value = "select password from front_desk where email=:email limit 1",
			nativeQuery = true
			)
	String findByEmailNative(@Param("email") String email);
	
	@Query(
			value = "update front_desk set password =:password where email=:email",
			nativeQuery = true
			)
	FrontDesk updatePassword(@Param("email") String email, @Param("password") String encodedPassword);

}

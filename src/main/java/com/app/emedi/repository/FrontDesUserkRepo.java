package com.app.emedi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.emedi.bean.entity.FrontDesk;
@Repository
public interface FrontDesUserkRepo extends JpaRepository<FrontDesk, Long>{

	FrontDesk findByUsername(String username);

	FrontDesk deleteByUsername(String usernaame);

}

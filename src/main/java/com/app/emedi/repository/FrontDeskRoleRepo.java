package com.app.emedi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.emedi.bean.entity.Role;
@Repository
public interface FrontDeskRoleRepo extends JpaRepository<Role, Long> {

}

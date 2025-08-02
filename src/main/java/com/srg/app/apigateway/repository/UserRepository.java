package com.srg.app.apigateway.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.srg.app.apigateway.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}

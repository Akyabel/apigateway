package com.srg.app.apigateway.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.srg.app.apigateway.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

}

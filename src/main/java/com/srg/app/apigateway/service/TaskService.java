package com.srg.app.apigateway.service;

import org.springframework.stereotype.Service;

import com.srg.app.apigateway.model.Task;
import com.srg.app.apigateway.repository.TaskRepository;

@Service
public class TaskService {
private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    public Task createTask(Task task){
        return taskRepository.save(task);
    }
}

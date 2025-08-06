package com.srg.app.apigateway.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.srg.app.apigateway.model.Task;
import com.srg.app.apigateway.repository.TaskRepository;
import com.srg.app.apigateway.service.TaskService;
import com.srg.app.dto.TaskDTO;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    private final TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @GetMapping("/")
    public ResponseEntity<List<TaskDTO>> getAllTasks(){
        List<TaskDTO> tasks = taskService.getAllTasks();
        
        return ResponseEntity.ok().body(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id){
        TaskDTO task = taskService.getTask(id);
        
        return ResponseEntity.ok(task);
    }

    @PostMapping("/")
    public ResponseEntity<String> createTask(@RequestBody TaskDTO taskDTO){

        Task createdTask = taskService.createTask(taskDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                            .body("Task created with id: " + createdTask.getId());
    } 

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTaskById(@PathVariable Long id, @RequestBody TaskDTO taskDTO){
        Task updatedTask = taskRepository.updatedTask(taskDTO);
        return ResponseEntity.ok("Task updated succesfully with id: " + updatedTask.getId();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id){
       taskRepository.deleteById(id);
       return ResponseEntity.ok().body("Task deleted successfully with id: " + id);
    } 

}

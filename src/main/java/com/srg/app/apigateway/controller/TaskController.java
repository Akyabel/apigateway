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
    public ResponseEntity<List<Task>> getAllTasks(){
        List<Task> tasks = taskRepository.findAll();
        
        return ResponseEntity.ok().body(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getTaskById(@PathVariable Long id){
        Task task = taskRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Task not found"));
        
        return ResponseEntity.ok().body(task);
    }

    @PostMapping("/")
    public ResponseEntity<Object> createTask(@RequestBody Task task){

        Task createdTask = taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED)
                            .body("Task created with id: " + createdTask.getId());
    } 

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTaskById(@PathVariable Long id, @RequestBody Task taskDetails){
        Task task = taskRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setTitulo(taskDetails.getTitulo());
        task.setDescripcion(taskDetails.getDescripcion());
        task.setEstado(taskDetails.getEstado());

        Task updatedTask = taskRepository.save(task);
        
        return ResponseEntity.ok().body("Task updated succesfully with id: " + updatedTask.getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTask(@PathVariable Long id){
       taskRepository.deleteById(id);
       return ResponseEntity.ok().body("Task deleted successfully with id: " + id);
    } 

}

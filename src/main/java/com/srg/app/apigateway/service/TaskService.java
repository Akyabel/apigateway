package com.srg.app.apigateway.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.srg.app.apigateway.model.Task;
import com.srg.app.apigateway.model.User;
import com.srg.app.apigateway.repository.TaskRepository;
import com.srg.app.dto.TaskDTO;

@Service
public class TaskService {
private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    public List<TaskDTO> getAllTasks(){
        return taskRepository.findAll().stream().map(this::convertToDTO).toList();
    }

    public TaskDTO getTask(Long id){
        Task task = taskRepository.findById(id)
        .orElseThrow(()-> new RuntimeException("Task not found :("));
        return convertToDTO(task);
    }

    public TaskDTO createTask(TaskDTO dto){
        Task task = convertToEntity(dto);
        Task saved = taskRepository.save(task);
        return convertToDTO(saved);
    }

    public TaskDTO updateTask(Long id, TaskDTO dto){
        Task task = taskRepository.findById(id)
        .orElseThrow(()-> new RuntimeException("Task not found :("));
        
        task.setTitulo(dto.getTitulo());
        task.setDescripcion(dto.getDescripcion());
        task.setEstado(dto.getEstado());

        Task updated = taskRepository.save(task);
        return convertToDTO(updated);
    }

    public void deletTask(Long id){
        Task task = taskRepository.findById(id)
        .orElseThrow(()-> new RuntimeException("Task not found :("));

        for(User user : task.getUser()){
            user.getTasks().remove(task);
        }

        taskRepository.delete(task);
    }

    public TaskDTO convertToDTO(Task task){
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setTitulo(task.getTitulo());
        dto.setDescripcion(task.getDescripcion());
        dto.setEstado(task.getEstado());

        return dto;
    }

    public Task convertToEntity(TaskDTO dto){
        Task task = new Task();
        
        if (dto.getId() != null){
        task.setId(dto.getId());
        }
        task.setTitulo(dto.getTitulo());
        task.setDescripcion(dto.getDescripcion());
        task.setEstado(dto.getEstado());

        return task;
    }
}

package com.srg.app.apigateway.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.srg.app.apigateway.model.Task;
import com.srg.app.apigateway.model.User;
import com.srg.app.apigateway.repository.TaskRepository;
import com.srg.app.apigateway.repository.UserRepository;
import com.srg.app.dto.TaskDTO;
import com.srg.app.dto.UserDTO;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public UserService(UserRepository userRepository, TaskRepository taskRepository){
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    public List<UserDTO> getAllUsers(){
        return userRepository.findAll().stream().map(this::convertUserToDTO).toList();
    }

    public UserDTO getUser(Long id){
        User user = userRepository.findById(id)
        .orElseThrow(()-> new RuntimeException("User not found :("));
        return convertUserToDTO(user);
    }

    public UserDTO createUser(UserDTO dto){
        User user = convertToEntity(dto);
        User saved = userRepository.save(user);
        return convertUserToDTO(saved);
    }

    public UserDTO updateUser(Long id, UserDTO dto){
        User user = userRepository.findById(id)
        .orElseThrow(()-> new RuntimeException("User not found :("));
        
        user.setNombre_usuario(dto.getNombre_usuario());
        user.setCorreo(dto.getCorreo());

        if (dto.getId() != null) {
            Set<Task> tasks = dto.getTasks().stream().map(taskDTO -> taskRepository.findById(taskDTO.getId())
                                                      .orElseThrow(()-> new RuntimeException("Task not found :(")))
                                                      .collect(Collectors.toSet());
            user.setTasks(tasks);
        }

        User updated = userRepository.save(user);
        return convertUserToDTO(updated);
    }

    public void deletUser(Long id){
        User user = userRepository.findById(id)
        .orElseThrow(()-> new RuntimeException("User not found :("));

        userRepository.delete(user);
    }


    public UserDTO convertUserToDTO(User user){
        Set<TaskDTO> taskDTOs = new HashSet<>(); 
        for (Task task: user.getTasks()){
            taskDTOs.add(convertTaskToDTO(task));
        }
        UserDTO dto = new UserDTO(user.getId(), user.getNombre_usuario(), user.getCorreo(), taskDTOs);

        return dto;
    }

    private TaskDTO convertTaskToDTO(Task task){
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setTitulo(task.getTitulo());
        dto.setDescripcion(task.getDescripcion());
        dto.setEstado(task.getEstado());

        return dto;
    }

    public User convertToEntity(UserDTO dto){
        User user = new User();
        
        if (dto.getId() != null){
        user.setId(dto.getId());
        }
        user.setNombre_usuario(dto.getNombre_usuario());
        user.setCorreo(dto.getCorreo());
        
        if (dto.getTasks() != null) {
        Set <Task> tasks = dto.getTasks().stream()
                                         .map(taskDTO -> {
                                            if (taskDTO.getId() != null) {
                                                return taskRepository.findById(taskDTO.getId())
                                                      .orElseThrow(()-> new RuntimeException("Task not found :("));
                                            }else{
                                                Task newTask = new Task();
                                                newTask.setTitulo(taskDTO.getTitulo());
                                                newTask.setDescripcion(taskDTO.getDescripcion());
                                                newTask.setEstado(taskDTO.getEstado());
                                                return newTask;
                                            }
                                         }).collect(Collectors.toSet());
        user.setTasks(tasks);
                }
         return user;
        }
       


    @Transactional
    public UserDTO addTaskToUser(Long userId, Long taskId) {
        User user = userRepository.findById(userId)
        .orElseThrow(()-> new RuntimeException("User not found :("));
        Task task = taskRepository.findById(taskId)
        .orElseThrow(()-> new RuntimeException("Task not found :("));

        user.getTasks().add(task);
        userRepository.save(user);

        return convertUserToDTO(user);
    }

    public UserDTO removeFromToUser(Long userId, Long taskId) {
        User user = userRepository.findById(userId)
        .orElseThrow(()-> new RuntimeException("User not found :("));
        Task task = taskRepository.findById(taskId)
        .orElseThrow(()-> new RuntimeException("Task not found :("));

        user.getTasks().remove(task);
        userRepository.save(user);

        return convertUserToDTO(user);
    }

}

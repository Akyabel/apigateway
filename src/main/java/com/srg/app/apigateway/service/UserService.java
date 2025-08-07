package com.srg.app.apigateway.service;

import org.springframework.stereotype.Service;

import com.srg.app.apigateway.model.User;
import com.srg.app.apigateway.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository, TaskRepository taskRespository){
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    public List<UserDTO> getAllUsers(){
        return userRepository.findAll().stream().map(this::convertUserToDTO).toList();
    }

    public UserDTO getUser(Long id){
        User user = userRepository.findById(id)
        .orElseThrow(()-> new RuntimeException("User not found :("));
        return convertToDTO(user);
    }

    public UserDTO createUser(UserDTO dto){
        User user = convertToEntity(dto);
        User saved = userRepository.save(user)
        return convertUserToDTO(saved);
    }

    public UserDTO updateUser(Long id, UserDTO dto){
        User user = userRepository.findById(id)
        .orElseThrow(()-> new RuntimeException("User not found :("));
        
        user.setNombre_usuario(dto.getNombre_usuario());
        user.setCorre(dto.getCorreo());

        Set <Task> tasks = dto.getTasks().stream().map(taskDTO -> TaskRepository.findById(taskDTO.getId())
                                                      .orElseThrow(()-> new RuntimeException("Task not found :(")))
                                                      .collect(Collectors.toSet());
        user.setTask(dto.getTask());

        User updated = taskRepository.save(user);
        return convertToDTO(updated);
    }

    public void deletUser(Long id){
        User user = userRepository.findById(id)
        .orElseThrow(()-> new RuntimeException("User not found :("));

        userRepository.delete(user);
    }


    public UserDTO convertUserToDTO(User user){
        Set<TaskDTO> taskDTOs = new HashSet()<>; 
        for (Task task: user.getTasks(){
            taskDTOs.add(convertTaskToDTO(task))
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
        
        Set <Task> tasks = dto.getTasks().stream().map(taskDTO -> TaskRepository.findById(taskDTO.getId())
                                                      .orElseThrow(()-> new RuntimeException("Task not found :(")))
                                                      .collect(Collectors.toSet());
        task.setTasks(dto.getTasks());

        return user;
    }

}

package com.srg.app.apigateway.controller;

import java.util.List;

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

import com.srg.app.apigateway.service.UserService;
import com.srg.app.dto.UserDTO;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        List<UserDTO> users = userService.getAllUsers();
        
        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id){
        UserDTO user = userService.getUser(id);
        
        return ResponseEntity.ok(user);
    }

    @PostMapping("/")
    public ResponseEntity<String> createUser(@RequestBody UserDTO userDTO){

        UserDTO createdUser = userService.createUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                            .body("User created with id: " + createdUser.getId());
    } 

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUserById(@PathVariable Long id, @RequestBody UserDTO userDetails){
        UserDTO updatedUser = userService.updateUser(id, userDetails);
        
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
       userService.deletUser(id);
       return ResponseEntity.ok("User deleted successfully with id: " + id);
    }
    
    @PostMapping("/{userId}/tasks/{taskId}")
    public ResponseEntity<UserDTO> addTaskToUser(@PathVariable Long userId, @PathVariable Long taskId){
        UserDTO updatedUser = userService.addTaskToUser(userId, taskId);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{userId}/tasks/{taskId}")
    public ResponseEntity<String> removeTaskFromUser(@PathVariable Long userId, @PathVariable Long taskId){
        UserDTO deleteUser = userService.removeFromToUser(userId, taskId);
        return ResponseEntity.ok("User deleted succesfully");
    }
}

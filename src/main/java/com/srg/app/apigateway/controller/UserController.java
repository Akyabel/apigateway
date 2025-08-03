package com.srg.app.apigateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.srg.app.apigateway.model.User;
import com.srg.app.apigateway.repository.UserRepository;
import com.srg.app.apigateway.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable Long id){
        User user = userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("User not found"));
        
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/")
    public ResponseEntity<Object> createUser(@RequestBody User user){

        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                            .body("User created with id: " + createdUser.getId());
    } 

}

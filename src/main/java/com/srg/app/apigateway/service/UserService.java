package com.srg.app.apigateway.service;

import org.springframework.stereotype.Service;

import com.srg.app.apigateway.model.User;
import com.srg.app.apigateway.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User createUser(User user){
        return userRepository.save(user);
    }

}

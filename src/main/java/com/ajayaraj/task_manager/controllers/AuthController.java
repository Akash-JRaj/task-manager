package com.ajayaraj.task_manager.controllers;

import com.ajayaraj.task_manager.models.User;
import com.ajayaraj.task_manager.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {

        if(authService.isUserNameTaken(user.getUserName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        User addeduser = authService.addUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(addeduser);
    }
}
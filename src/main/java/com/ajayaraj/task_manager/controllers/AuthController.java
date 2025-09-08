package com.ajayaraj.task_manager.controllers;

import com.ajayaraj.task_manager.dtos.LoginRequest;
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

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        if(!authService.isUserExists(loginRequest.getUserName())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with user name " + loginRequest.getUserName() + " not found!");
        }

        if(!authService.isValidLoginRequest(loginRequest)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User name or Password is Incorrect!");
        }

        return ResponseEntity.status(HttpStatus.OK).body(authService.login(loginRequest));
    }
}
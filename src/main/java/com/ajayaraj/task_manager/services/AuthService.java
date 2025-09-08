package com.ajayaraj.task_manager.services;

import com.ajayaraj.task_manager.utilis.JwtUtil;
import com.ajayaraj.task_manager.dtos.LoginRequest;
import com.ajayaraj.task_manager.models.User;
import com.ajayaraj.task_manager.repos.AuthRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthRepo authRepo;

    @Autowired
    private JwtUtil jwtUtil;

    public User addUser(User user) {

        user.setPassword(getHashedPassword(user.getPassword()));

        return authRepo.save(user);
    }

    public String login(LoginRequest loginRequest) {
        return jwtUtil.generateJwtToken(loginRequest.getUserName());
    }

    public User getByUserName(String userName) {
        return authRepo.findByUserName(userName);
    }

    public boolean isUserNameTaken(String userName) {
        return authRepo.findByUserName(userName) != null;
    }

    public boolean isUserExists(String userName) {
        return authRepo.findByUserName(userName) != null;
    }

    public String getHashedPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    public boolean isValidLoginRequest(LoginRequest loginRequest) {

        String password = loginRequest.getPassword();
        String encodedPassword = authRepo.findByUserName(loginRequest.getUserName()).getPassword();

        return new BCryptPasswordEncoder().matches(password, encodedPassword);
    }

}

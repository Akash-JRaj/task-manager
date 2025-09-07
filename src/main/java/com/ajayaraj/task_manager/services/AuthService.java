package com.ajayaraj.task_manager.services;

import com.ajayaraj.task_manager.models.User;
import com.ajayaraj.task_manager.repos.AuthRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthRepo authRepo;

    public User addUser(User user) {

        user.setPassword(getHashedPassword(user.getPassword()));

        return authRepo.save(user);
    }

    public boolean isUserNameTaken(String userName) {
        return authRepo.findByUserName(userName) != null;
    }

    public String getHashedPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

}

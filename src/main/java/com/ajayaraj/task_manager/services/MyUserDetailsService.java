package com.ajayaraj.task_manager.services;

import com.ajayaraj.task_manager.models.User;
import com.ajayaraj.task_manager.models.UserPrincipal;
import com.ajayaraj.task_manager.repos.AuthRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private AuthRepo authRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = authRepo.findByUserName(username);

        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new UserPrincipal(user);
    }
}

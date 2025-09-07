package com.ajayaraj.task_manager.repos;

import com.ajayaraj.task_manager.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuthRepo extends JpaRepository<User, UUID> {
    User findByUserName(String userName);
}

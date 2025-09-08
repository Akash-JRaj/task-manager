package com.ajayaraj.task_manager.dtos;

import lombok.Data;

@Data
public class LoginRequest {
    private String userName;
    private String password;
}

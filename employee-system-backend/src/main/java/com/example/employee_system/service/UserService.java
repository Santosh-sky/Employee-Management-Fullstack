package com.example.employee_system.service;

import com.example.employee_system.dto.LoginRequest;
import com.example.employee_system.entity.User;

public interface UserService {

        User register(User user);

        String login(LoginRequest loginRequest);
}

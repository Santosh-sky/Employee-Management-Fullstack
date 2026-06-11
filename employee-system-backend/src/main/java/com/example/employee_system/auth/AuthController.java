package com.example.employee_system.auth;

import com.example.employee_system.dto.ApiResponse;
import com.example.employee_system.dto.LoginRequest;
import com.example.employee_system.dto.LoginResponse;
import com.example.employee_system.entity.User;
import com.example.employee_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }

    /*@PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }*/

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(
            @RequestBody LoginRequest loginRequest) {

        String token = userService.login(loginRequest);

        LoginResponse loginResponse = new LoginResponse(token);

        return new ApiResponse<>(
                true,
                "Login successful",
                loginResponse
        );
    }
}

package com.example.employee_system.service;

import com.example.employee_system.dto.LoginRequest;
import com.example.employee_system.entity.User;
import com.example.employee_system.exception.InvalidCredentialsException;
import com.example.employee_system.repository.UserRepository;
import com.example.employee_system.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Override
    public User register(User user) {
        //user.setPassword(passwordEncoder.encode(user.getPassword()));

        String encodedPassword =
                passwordEncoder.encode(user.getPassword());

        System.out.println(encodedPassword);

        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    @Override
    public String login(LoginRequest loginRequest) {

        Optional<User> optionalUser =
                userRepository.findByEmail(loginRequest.getEmail());

        if (optionalUser.isEmpty()) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        User user = optionalUser.get();

        boolean isPasswordMatch =
                passwordEncoder.matches(
                        loginRequest.getPassword(),
                        user.getPassword()
                );

        if (!isPasswordMatch) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        return jwtService.generateToken(user.getEmail(), user.getRole());
    }

}

package com.example.Todo.service;

import com.example.Todo.dto.user.RegisterRequest;
import com.example.Todo.dto.user.LoginRequest;
import com.example.Todo.entity.User;
import com.example.Todo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder=passwordEncoder;
    }

    public void createUser(RegisterRequest request){
        User savedUser = new User(
                request.getName(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                LocalDateTime.now()
        );

        userRepository.save(savedUser);
    }

    public LoginRequest mapToResponse(User user){
        return new LoginRequest(user.getName(), user.getEmail());
    }
}

package com.example.Todo.controller;

import com.example.Todo.dto.user.LoginRequest;
import com.example.Todo.dto.user.RegisterRequest;
import com.example.Todo.entity.User;
import com.example.Todo.jwt.JwtUtil;
import com.example.Todo.repository.UserRepository;
import com.example.Todo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(JwtUtil jwtUtil, UserRepository userRepository, UserService userService,
                          PasswordEncoder passwordEncoder) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest body){
        String email = body.getEmail();
        String password = passwordEncoder.encode(body.getPassword());

        if (userRepository.findByEmail(email).isPresent())
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");

        userService.createUser(body);
        return new ResponseEntity<>("Successfully registered",HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest body){
        String email = body.getEmail();
        String password = body.getPassword();

        var userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()){
            return new ResponseEntity<>("User not registered", HttpStatus.UNAUTHORIZED);
        }

        User user = userOptional.get();

        if(!passwordEncoder.matches(password, user.getPassword())){
            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }

        String token = jwtUtil.generateToken(email);

        return ResponseEntity.ok(Map.of("token" , token));


    }

}

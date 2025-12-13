package com.example.demo.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.data.AuthRequest;
import com.example.demo.data.AuthResponse;
import com.example.demo.model.UserEntity;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.JwtService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, UserRepo userRepo , 
            PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public String register(@RequestBody AuthRequest request) {
        var user = UserEntity.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request
                        .getPassword()))
                .build();

        userRepo.save(user);
        return "User saved sucessfully";
    }
    
    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
            )
        );
        String token = jwtService.generateToken(request.getUsername());
        
        return new AuthResponse(token);
    }
    

}

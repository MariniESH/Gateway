package com.example.apigateway.controller;

import com.example.apigateway.entity.AuthRequest;
import com.example.apigateway.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class AuthController {

    private final ReactiveAuthenticationManager authenticationManager;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    public AuthController(ReactiveAuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/authenticate")
    public Mono<String> generateToken(@RequestBody AuthRequest authRequest) {
        // Create the authentication token using the provided username and password
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                authRequest.getUsername(), authRequest.getPassword());

        return authenticationManager.authenticate(token)  // Authenticate the user reactively
                .map(authentication -> jwtUtil.generateToken(authRequest.getUsername()))
                .onErrorResume(e -> Mono.error(new RuntimeException("Authentication failed"))); // Handle authentication failure
    }


}

package com.example.apigateway.entity;

import org.springframework.beans.factory.annotation.Value;


public class AuthRequest {

//    @Value("${spring.security.user.name}")
    private String username;
//    @Value("${spring.security.user.password}")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

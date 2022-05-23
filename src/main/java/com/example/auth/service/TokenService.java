package com.example.auth.service;

public interface TokenService {
    String generateToken(String clientId);
}

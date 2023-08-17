package com.memora.api.service;

public interface JwtService {
    String extractUserEmail(String token);
    String generateToken(String email);
    boolean isValidToken(String token, String email);
}

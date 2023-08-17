package com.memora.api.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String extractUserEmail(String token);
    String generateToken(String email);
    boolean isValidToken(String token, UserDetails userDetails);
}

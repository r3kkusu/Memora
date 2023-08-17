package com.memora.api.common.validator;

public class AuthValidator {
    public static boolean isValidPassword(String password) {
        // Implement your password validation logic here
        return password != null && password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!_\\-])(?=\\S+$).{8,32}$");
    }
}
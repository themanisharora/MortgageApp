package com.hackathon.MortgageDemo.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface AuthService {
    boolean authenticate(String username, String password) throws InvalidKeySpecException, NoSuchAlgorithmException;
    boolean isValidUser(String username);
}

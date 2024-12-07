package com.hackathon.MortgageDemo.service.Impl;

import com.hackathon.MortgageDemo.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Override
    public boolean authenticate(String username, String password) {
        return true;
    }
}

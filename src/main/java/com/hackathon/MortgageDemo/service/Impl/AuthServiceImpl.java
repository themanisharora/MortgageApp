package com.hackathon.MortgageDemo.service.Impl;

import com.hackathon.MortgageDemo.entity.Customer;
import com.hackathon.MortgageDemo.repository.CustomerRepository;
import com.hackathon.MortgageDemo.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    CustomerRepository customerRepository;

    @Value("${salt.simple}")
    private String simpleSalt;

    @Override
    public boolean authenticate(String username, String password) throws InvalidKeySpecException, NoSuchAlgorithmException {
        Optional<Customer> customerOptional = customerRepository.findByCustomerName(username);
        if (customerOptional.isEmpty())
            return false;

        String customerPassword = customerOptional.map(Customer::getPassword).get();
        KeySpec spec = new PBEKeySpec(customerPassword.toCharArray(), simpleSalt.getBytes(), 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = factory.generateSecret(spec).getEncoded();

        return password.equals(new String(hash, StandardCharsets.UTF_8));
    }

    @Override
    public boolean isValidUser(String username) {
        return customerRepository.findByCustomerName(username).isPresent();
    }
}

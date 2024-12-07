package com.hackathon.MortgageDemo.filter;

import com.hackathon.MortgageDemo.service.AuthService;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class AuthFilter extends OncePerRequestFilter {

    private final AuthService authService;

    @Value("${sigining.key}")
    private String signingKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().startsWith("/auth")) {
            filterChain.doFilter(request, response);
        }

        final String authorization = request.getHeader("Authorization");
        if (authorization == null) {
            response.setStatus(401);
            return;
        }

        try {
            if (authorization.toLowerCase().startsWith("basic")) {
                handleBasicAuth(request, response, filterChain);
            } else if (authorization.toLowerCase().startsWith("bearer")) {
                handleBearerAuth(request, response, filterChain);
            } else {
                response.setStatus(401);
            }
        } catch (Exception e) {
            response.setStatus(401);
        }
    }

    private void handleBasicAuth(HttpServletRequest request,
                                 HttpServletResponse response,
                                 FilterChain filterChain) throws Exception {
        final String authorization = request.getHeader("Authorization");
        String base64Credentials = authorization.substring("Basic".length()).trim();
        String[] values = decodeAuth(base64Credentials);

        if (!validAuth(values[0], values[1])) {
            response.setStatus(401);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void handleBearerAuth(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {
        final String authorization = request.getHeader("Authorization");
        String jwtToken = authorization.substring("Bearer".length()).trim();

        String subject = Jwts.parser()
                .setSigningKey(signingKey)
                .parseClaimsJws(jwtToken)
                .getBody()
                .getSubject();

        if (subject != null && isValidUser(subject)) {
            filterChain.doFilter(request, response);
            return;
        }

        response.setStatus(401);
    }


    private boolean validAuth(String username, String password) throws InvalidKeySpecException, NoSuchAlgorithmException {
        return authService.authenticate(username, password);
    }

    private boolean isValidUser(String username) {
        return authService.isValidUser(username);
    }

    private String[] decodeAuth(String base64Credentials) {
        String credentials = base64Credentials;
        try {
            byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
            credentials = new String(credDecoded, StandardCharsets.UTF_8);
        } catch(IllegalArgumentException e) {
        }
        return credentials.split(":", 2);
    }
}

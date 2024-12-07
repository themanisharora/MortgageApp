package controller;

import com.hackathon.MortgageDemo.model.BaseResponse;
import com.hackathon.MortgageDemo.model.LoginRequest;
import com.hackathon.MortgageDemo.model.TransactionRequest;
import com.hackathon.MortgageDemo.service.AuthService;
import com.hackathon.MortgageDemo.utils.ResponseBuilder;
import constants.AppConstants;
import constants.Status;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {
    @Value("${token.validity}")
    private Long tokenValidity;

    @Value("${sigining.key}")
    private String signingKey;

    private final AuthService authService;

    @PostMapping("/auth")
    public ResponseEntity<BaseResponse<String>> authenticate(@RequestBody LoginRequest request) {
        boolean validAuth = authService.authenticate(request.getUsername(), request.getPassword());
        if (!validAuth) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token =  Jwts.builder()
                .setSubject(request.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + tokenValidity))
                .signWith(SignatureAlgorithm.HS512, signingKey)
                .compact();
        return ResponseBuilder.success(HttpStatus.OK,AppConstants.TRANSACTION_SUCCESS,token);
    }

}
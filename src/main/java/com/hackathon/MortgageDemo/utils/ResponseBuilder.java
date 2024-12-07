package com.hackathon.MortgageDemo.utils;

import com.hackathon.MortgageDemo.model.BaseResponse;
import constants.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseBuilder {

    public static <T> ResponseEntity<BaseResponse<T>> success(HttpStatus status,String message,T data) {
        return ResponseEntity.status(status).body(new BaseResponse<>(message, Status.SUCCESS,data));
    }
}

package com.hackathon.MortgageDemo.exception;

import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.websocket.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ControllerAdvicer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerAdvicer.class);

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex) {
        LOGGER.error("handleAuthenticationException : " + ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse("401", "Unauthorized" + ex.getMessage()));

    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleBadRequest(IllegalArgumentException ex, WebRequest request) {
        LOGGER.error("handleBadRequest : " + ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("400", "Bad Request" + ex.getMessage()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleNotFound(ResourceNotFoundException ex, WebRequest request) {
        LOGGER.error("handleNotFound : " + ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("404", "Not Found" + ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleInternalServerError(Exception ex, WebRequest request) {
        LOGGER.error("handleInternalServerError : " + ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("500", "Internal Server Error: An unexpected error occurred"));
    }

    @Getter
    @Setter
    static class ErrorResponse {
        private String code;
        private String message;

        public ErrorResponse(String code, String message) {
            this.code = code;
            this.message = message;
        }

    }
}




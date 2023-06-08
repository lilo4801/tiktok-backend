package com.example.tiktok.exceptions;

import com.example.tiktok.models.responses.ResponseHandler;
import com.example.tiktok.utils.LanguageUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUnwantedException(Exception e) {
        return ResponseHandler.generateFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null);
    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<?> handleAuthorisedException(BadCredentialsException e) {
        return ResponseHandler.generateFailureResponse(HttpStatus.UNAUTHORIZED, e.getMessage(), null);
    }

    @ExceptionHandler({NotFoundException.class})  //
    public ResponseEntity<?> handleNotFoundException(NotFoundException e, WebRequest req) {
        return ResponseHandler.generateFailureResponse(HttpStatus.NOT_FOUND, e.getMessage(), null);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})  //
    public ResponseEntity<?> handleArgumentException(MethodArgumentNotValidException e, WebRequest req) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return ResponseHandler.generateFailureResponse(HttpStatus.BAD_REQUEST, LanguageUtils.getMessage("message.validation.error"), errors);
    }
}

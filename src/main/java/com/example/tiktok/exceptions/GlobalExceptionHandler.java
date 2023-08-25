package com.example.tiktok.exceptions;

import com.example.tiktok.models.responses.ResponseHandler;
import com.example.tiktok.utils.LanguageUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUnwantedException(Exception e) {
        return ResponseHandler.generateFailureResponseWithDefaultMsg(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null);
    }

    @ExceptionHandler({DataAccessException.class})  //
    public ResponseEntity<?> handleSQLException(DataAccessException e) {
        return ResponseHandler.generateFailureResponseWithDefaultMsg(HttpStatus.BAD_REQUEST, e.getMessage(), null);
    }

    @ExceptionHandler({IOException.class, LoginExeception.class})
    public ResponseEntity<?> handleIOException(Exception e) {
        return ResponseHandler.generateFailureResponseWithDefaultMsg(HttpStatus.BAD_REQUEST, e.getMessage(), null);
    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<?> handleAuthorisedException(BadCredentialsException e) {
        return ResponseHandler.generateFailureResponseWithDefaultMsg(HttpStatus.UNAUTHORIZED, e.getMessage(), null);
    }

    @ExceptionHandler({CreateFailureException.class, UpdateFailtureException.class})  //
    public ResponseEntity<?> handleCreateFallException(Exception e) {
        return ResponseHandler.generateFailureResponse(HttpStatus.BAD_REQUEST, e.getMessage(), null);
    }


    @ExceptionHandler({NotFoundException.class})  //
    public ResponseEntity<?> handleNotFoundException(Exception e) {
        return ResponseHandler.generateFailureResponse(HttpStatus.NOT_FOUND, e.getMessage(), null);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})  //
    public ResponseEntity<?> handleArgumentException(MethodArgumentNotValidException e) {
        LOGGER.error("handle argument errors");
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return ResponseHandler.generateFailureResponse(HttpStatus.BAD_REQUEST, "message.validation.error", errors);
    }
}

package com.example.tiktok.models.responses;

import com.example.tiktok.utils.LanguageUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> generateSuccessResponse(HttpStatus status, String message, Object responseObj) {
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("timestamp", new Date());
        map.put("status", status.value());
        map.put("isSuccess", true);
        map.put("message", LanguageUtils.getMessage(message));
        map.put("data", responseObj);

        return new ResponseEntity<Object>(map, status);

    }

    public static ResponseEntity<Object> generateFailureResponse(HttpStatus status, String message, Object responseObj) {
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("timestamp", new Date());
        map.put("status", status.value());
        map.put("isSuccess", false);
        map.put("message", LanguageUtils.getMessage(message));
        map.put("details", responseObj);

        return new ResponseEntity<Object>(map, status);

    }

    public static ResponseEntity<Object> generateFailureResponseWithDefaultMsg(HttpStatus status, String message, Object responseObj) {
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("timestamp", new Date());
        map.put("status", status.value());
        map.put("isSuccess", false);
        map.put("message", message);
        map.put("details", responseObj);

        return new ResponseEntity<Object>(map, status);

    }
}

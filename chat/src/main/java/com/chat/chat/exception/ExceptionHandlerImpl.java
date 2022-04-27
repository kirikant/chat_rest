package com.chat.chat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;

@ControllerAdvice
public class ExceptionHandlerImpl{

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleException(EntityNotFoundException e){
        String message = e.getMessage();
        HashMap<String, String> response = new HashMap<>();
        response.put("message",message);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }


    @ExceptionHandler(WrongParametersException.class)
    public ResponseEntity<?> handleException(WrongParametersException e){
        String message = e.getMessage();
        HashMap<String, String> response = new HashMap<>();
        response.put("message",message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}

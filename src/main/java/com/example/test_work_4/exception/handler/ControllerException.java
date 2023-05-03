package com.example.test_work_4.exception.handler;

import com.example.test_work_4.exception.PasteBadRequestException;
import com.example.test_work_4.exception.PasteNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerException {

    @ExceptionHandler(PasteBadRequestException.class)
    public ResponseEntity<?> handleArgumentNotValid() {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(PasteNotFoundException.class)
    public ResponseEntity<?> handleNotFound() {
        return ResponseEntity.notFound().build();
    }

}

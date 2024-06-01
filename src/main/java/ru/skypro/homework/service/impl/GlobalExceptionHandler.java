package ru.skypro.homework.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.skypro.homework.exception.FileProcessingException;
import ru.skypro.homework.exception.ImageNotFoundException;
import ru.skypro.homework.exception.IncorrectCurrentPasswordException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({IncorrectCurrentPasswordException.class, UsernameNotFoundException.class})
    public ResponseEntity<String> handleIncorrectCurrentPasswordException(IncorrectCurrentPasswordException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    @ExceptionHandler({ImageNotFoundException.class, FileProcessingException.class})
    public ResponseEntity<String> handleImageNotFoundException(ImageNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

}

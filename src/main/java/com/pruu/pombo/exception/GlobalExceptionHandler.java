package com.pruu.pombo.exception;
import java.util.HashMap;
import java.util.Map;

import com.pruu.pombo.exception.PomboException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    //Solução 2: Interceptar todas as PomboException lançadas
    //Fonte: https://www.geeksforgeeks.org/exception-handling-in-spring-boot/
    @ExceptionHandler(PomboException.class)
    public ResponseEntity<String> handlePomboException(PomboException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap();

        // Itera sobre os erros de campo e coleta as mensagens
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // Outros manipuladores de exceção...
}
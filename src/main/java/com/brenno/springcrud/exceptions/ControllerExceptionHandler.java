package com.brenno.springcrud.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerExceptionHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorException> methodArgumentNotValidExcetionHandler(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getObjectName()+ " : " +error.getDefaultMessage())
                .collect(Collectors.toList());

        ApiErrorException apiErrorException = new ApiErrorException(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                errors);

        return new ResponseEntity<>(apiErrorException, HttpStatus.valueOf(apiErrorException.getStatusCode()));
    }

    @ExceptionHandler(PessoaException.class)
    public ResponseEntity<ApiErrorException> PessoaExceptionExcetionHandler(PessoaException ex) {
        List<String> errors = ex.getErrors();

        ApiErrorException apiErrorException = new ApiErrorException(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                errors);

        return new ResponseEntity<>(apiErrorException, HttpStatus.valueOf(apiErrorException.getStatusCode()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorException> globalExceptionHandler(Exception ex) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());

        ApiErrorException apiErrorException = new ApiErrorException(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                errors);

        return new ResponseEntity<>(apiErrorException, HttpStatus.valueOf(apiErrorException.getStatusCode()));
    }
}

package com.example.dacn.controller;

import com.example.dacn.basetemplate.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestControllerAdviceError {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse<?>> handleValidateException(HttpServletRequest request,
                                                                       MethodArgumentNotValidException e) {
        Map<String, List<String>> errors = e.getFieldErrors().stream()
                .collect(Collectors.groupingBy(FieldError::getField, Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())));

        return ResponseEntity.badRequest().body(ErrorResponse.builder()
                .url(request.getRequestURI())
                .message("Lỗi dữ liệu đầu vào")
                .status(HttpStatus.BAD_REQUEST)
                .data(errors)
                .build());
    }




}

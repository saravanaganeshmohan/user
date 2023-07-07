package com.bank.user.controller;

import com.bank.user.application.user.model.Response;
import com.bank.user.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public abstract class ControllerException {
    private static Logger log = LoggerFactory.getLogger(ControllerException.class);
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.info("Inside ControllerException handleValidateException method :" + ex);
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
    @ExceptionHandler(MultipartException.class)
    public ResponseEntity handleMaxSizeException(MaxUploadSizeExceededException ex) {
        log.info("Inside ControllerException handleMaxSizeException method :" + ex);
        Map<String, String> errors = new HashMap<>();
        errors.put("error","Maximum file size reached");
        return ResponseEntity.ok().body(new Response<>(Constant.ERROR_RESPONSE_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR.value(), errors));
    }
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity HttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex) {
        log.info("Inside ControllerException HttpMediaTypeNotSupportedException method :" + ex);
        Map<String, String> errors = new HashMap<>();
        errors.put("error","Unsupported Media Type");
        return ResponseEntity.ok().body(new Response<>(Constant.ERROR_RESPONSE_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR.value(), errors));
    }
}

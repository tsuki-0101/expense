package com.shivam.expenseSplitter.exceptions;


import com.shivam.expenseSplitter.response.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> ResourceNotFoundExceptionHandler(
            ResourceNotFoundException ex) {
        String message = ex.getMessage();
        ApiResponse response = new ApiResponse(message, false);
        return new ResponseEntity(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> MethodArgumentNotValidExceptionHandler(
            MethodArgumentNotValidException ex) {
        Map<String, String> resp = new HashMap();
        ex.getBindingResult()
                .getAllErrors()
                .forEach((error) -> {
                    String fieldName = ((FieldError) error).getField();
                    String message = error.getDefaultMessage();
                    resp.put(fieldName, message);
                });

        return new ResponseEntity(resp, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse> ApiExceptionHandler(ApiException ex) {
        String message = ex.getMessage();
        ApiResponse response = new ApiResponse(message, true);
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolation(
            DataIntegrityViolationException ex, WebRequest request) {
        System.out.println("dnzcgxcv   " + request);
        if (ex.getCause() instanceof ConstraintViolationException) {
            ApiResponse response = new ApiResponse("Database error", false);
            return new ResponseEntity(response, HttpStatus.CONFLICT);
        }
        ApiResponse response = new ApiResponse("Database error", false);
        return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
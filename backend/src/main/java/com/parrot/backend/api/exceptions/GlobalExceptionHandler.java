package com.parrot.backend.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<Object> handleNotFoundException(NotFoundException notFoundException) {
    ApiException apiException = new ApiException(notFoundException.getMessage(), notFoundException.getCause(), HttpStatus.NOT_FOUND);

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiException);
  }
  @ExceptionHandler(UserAlreadyExistsException.class)
  public ResponseEntity<Object> handleUserAlreadyExistsException(UserAlreadyExistsException userAlreadyExistsException) {
    ApiException apiException = new ApiException(userAlreadyExistsException.getMessage(), userAlreadyExistsException.getCause(), HttpStatus.CONFLICT);

    return ResponseEntity.status(HttpStatus.CONFLICT).body(apiException);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException methodArgumentNotValidException) {
    var validationError = new Validation("Request validation errors", methodArgumentNotValidException.getCause(), HttpStatus.BAD_REQUEST);

    methodArgumentNotValidException.getBindingResult().getAllErrors().forEach((error) -> {
      validationError.addError(((FieldError) error).getField(), error.getDefaultMessage());
    });

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationError);
  }
}

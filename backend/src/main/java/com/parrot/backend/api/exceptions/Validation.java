package com.parrot.backend.api.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class Validation extends ApiException {

  @Getter
  private List<FieldErrorMessage> errors = new ArrayList<>();

  public Validation(String message, Throwable throwable, HttpStatus httpStatus) {
    super(message, throwable, httpStatus);
  }

  public void addError(String fieldName, String message) {
    errors.add(new FieldErrorMessage(fieldName, message));
  }
}

package com.parrot.backend.data.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FieldErrorMessage {
  private String fieldName;
  private String fieldMessage;
}

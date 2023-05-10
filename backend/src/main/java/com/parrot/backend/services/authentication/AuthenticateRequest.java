package com.parrot.backend.services.authentication;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class AuthenticateRequest {
  @NotBlank(message = "This field cannot be empty")
  public String email;

  @NotBlank(message = "This field cannot be empty")
  public String password;
}

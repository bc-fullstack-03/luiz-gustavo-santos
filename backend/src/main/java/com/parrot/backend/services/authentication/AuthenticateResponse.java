package com.parrot.backend.services.authentication;

import lombok.Data;

import java.util.UUID;

@Data
public class AuthenticateResponse {
  public UUID id;
  public String token;
}

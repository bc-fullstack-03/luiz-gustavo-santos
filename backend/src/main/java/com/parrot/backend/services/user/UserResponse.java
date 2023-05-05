package com.parrot.backend.services.user;

import lombok.Data;

import java.util.UUID;
@Data
public class UserResponse {
  public UUID id;
  public String name;
  public String email;

  public UserResponse(UUID id, String name, String email) {
    this.id = id;
    this.name = name;
    this.email = email;
  }
}

package com.parrot.backend.services.user;

import lombok.Data;

import java.util.UUID;
@Data
public class UserResponse {
  public UUID id;
  public String name;
  public String email;
  private String photoUrl;

  public UserResponse(UUID id, String name, String email, String photoUrl) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.photoUrl = photoUrl;
  }
}

package com.parrot.backend.services.post;

import lombok.Data;

import java.util.UUID;

@Data
public class PostResponse {
  public UUID id;
  public String content;
  public String image;
  public UUID userId;

  public PostResponse(UUID id, String content, String image, UUID userId) {
    this.id = id;
    this.content = content;
    this.image = image;
    this.userId = userId;
  }
}

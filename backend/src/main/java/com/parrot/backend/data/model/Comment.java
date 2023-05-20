package com.parrot.backend.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Comment {

  @Id
  public UUID id;
  public String content;
  public UUID userId;
  public LocalDateTime createdAt;
  public LocalDateTime updatedAt;

  public Comment(String content, UUID userId) {
    setId();
    this.content = content;
    this.userId = userId;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }

  protected void setId() {
    this.id = UUID.randomUUID();
  }

  public UUID getId() {
    return this.id;
  }
}

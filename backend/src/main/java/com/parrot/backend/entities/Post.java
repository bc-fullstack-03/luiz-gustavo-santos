package com.parrot.backend.entities;

import com.parrot.backend.data.model.Comment;
import com.parrot.backend.data.model.Friend;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class Post {
  @Id
  private UUID id;
  private UUID userId;
  private String userName;
  private String content;
  private String image;
  private List<Comment> comments;
  private List<UUID> likes;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public Post(String content) {
    this.setId();
    this.content = content;
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

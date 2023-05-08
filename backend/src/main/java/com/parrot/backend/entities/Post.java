package com.parrot.backend.entities;

import com.parrot.backend.data.model.Comment;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.UUID;

@Data
public class Post {
  @Id
  private UUID id;
  private UUID userId;
  private String content;
  private String image;
  private List<Comment> comments;
  private List<UUID> likes;

  public Post(String content) {
    this.setId();
    this.content = content;
  }

  protected void setId() {
    this.id = UUID.randomUUID();
  }

  public UUID getId() {
    return this.id;
  }
}

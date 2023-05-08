package com.parrot.backend.services.post;

import com.parrot.backend.data.model.Comment;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class PostResponse {
  public UUID id;
  public String content;
  public String image;
  public UUID userId;
  public List<Comment> comments;

  public PostResponse(UUID id, String content, String image, UUID userId, List<Comment> comments) {
    this.id = id;
    this.content = content;
    this.image = image;
    this.userId = userId;
    this.comments = comments;
  }
}

package com.parrot.backend.services.post;

import com.parrot.backend.data.model.Comment;
import com.parrot.backend.entities.Post;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class PostResponse {
  public UUID id;
  public String content;
  public String image;
  public UUID userId;
  public String userName;
  public List<Comment> comments;
  public List<UUID> likes;
  public LocalDateTime createdAt;
  public LocalDateTime updatedAt;

  public PostResponse(Post post) {
    this.id = post.getId();
    this.content = post.getContent();
    this.image = post.getImage();
    this.userId = post.getUserId();
    this.userName = post.getUserName();
    this.comments = post.getComments() == null ? new ArrayList<>() : post.getComments();
    this.likes = post.getLikes() == null ? new ArrayList<>() : post.getLikes();
    this.createdAt = post.getCreatedAt();
    this.updatedAt = post.getUpdatedAt();
  }
}

package com.parrot.backend.services.post;

import com.parrot.backend.data.model.Comment;
import com.parrot.backend.entities.Post;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class PostResponse {
  public UUID id;
  public String content;
  public String image;
  public UUID userId;
  public Integer comments;
  public Integer likes;

  public PostResponse(Post post) {
    this.id = post.getId();
    this.content = post.getContent();
    this.image = post.getImage();
    this.userId = post.getUserId();
    this.comments = post.getComments() == null ? 0 : post.getComments().size();
    this.likes = post.getLikes() == null ? 0 : post.getLikes().size();
  }
}

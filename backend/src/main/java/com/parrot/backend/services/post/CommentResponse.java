package com.parrot.backend.services.post;

import com.parrot.backend.data.model.Comment;
import lombok.Data;

@Data
public class CommentResponse {
  public Comment comment;

  public CommentResponse(Comment comment) {
    this.comment = comment;
  }
}

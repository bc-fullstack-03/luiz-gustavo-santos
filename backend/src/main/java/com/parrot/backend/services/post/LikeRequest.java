package com.parrot.backend.services.post;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class LikeRequest {
  @NotNull(message = "This field cannot be empty")
  public UUID postId;
}

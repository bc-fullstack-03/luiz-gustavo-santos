package com.parrot.backend.data.model;

import com.parrot.backend.entities.User;
import lombok.Data;

import java.util.UUID;

@Data
public class Friend {
  public UUID id;
  public String name;
  public String photoUrl;

  public Friend(User user) {
    this.id = user.getId();
    this.name = user.getName();
    this.photoUrl = user.getPhotoUrl();
  }
}

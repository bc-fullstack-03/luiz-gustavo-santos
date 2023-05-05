package com.parrot.backend.services.user;

import com.parrot.backend.entities.User;

import java.util.List;
import java.util.UUID;

public interface IUserService {
  public void create(CreateUserRequest request);
  public UserResponse findByEmail(String email);
  public UserResponse findById(UUID id);
  public List<User> findAll();
  public UserResponse update(UUID id, UpdateUserRequest request);
  public void delete(UUID id);
}

package com.parrot.backend.services.user;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface IUserService {
  public void create(CreateUserRequest request);
  public UserResponse findByEmail(String email);
  public UserResponse findById(UUID id);
  public List<UserResponse> findAll();
  public UserResponse update(UUID id, UpdateUserRequest request);
  public void delete(UUID id);
  public void uploadPhoto(UUID id, MultipartFile photo) throws Exception;
}

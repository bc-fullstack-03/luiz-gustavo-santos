package com.parrot.backend.services.user;

import com.parrot.backend.entities.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface IUserService {
  void create(CreateUserRequest request);
  UserResponse findByEmail(String email);
  UserResponse findById(UUID id);
  List<UserResponse> findAll();
  UserResponse update(UUID id, UpdateUserRequest request);
  void delete(UUID id);
  void uploadPhoto(MultipartFile photo) throws Exception;
  User getUser(String email);
  User getUserById(UUID id);
}

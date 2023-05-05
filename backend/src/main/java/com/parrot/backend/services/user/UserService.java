package com.parrot.backend.services.user;

import com.parrot.backend.api.exceptions.NotFoundException;
import com.parrot.backend.api.exceptions.UserAlreadyExistsException;
import com.parrot.backend.data.IUserRepository;
import com.parrot.backend.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UserService implements IUserService {
  @Autowired
  private IUserRepository userRepository;

  @Transactional
  public void create(CreateUserRequest request) {
    var userAlreadyExists = userRepository.findUserByEmail(request.email);
    if(userAlreadyExists.isPresent()) {
      throw new UserAlreadyExistsException("User already exists");
    }

    var user = new User(request.name, request.email, request.password);

    userRepository.save(user);
  }

  public UserResponse findByEmail(String email) {
    var user = userRepository.findUserByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));

    return new UserResponse(user.getId(), user.getName(), user.getEmail());
  }

  public UserResponse findById(UUID id) {
    var user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));

    return new UserResponse(user.getId(), user.getName(), user.getEmail());
  }

  public List<User> findAll() {
    return userRepository.findAll();
  }

  @Transactional
  public UserResponse update(UUID id, UpdateUserRequest request) {
    var user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    if(request.getName() != null) user.setName(request.name);
    if(request.getPassword() != null) user.setPassword(request.password);

    userRepository.save(user);

    return new UserResponse(user.getId(), user.getName(), user.getEmail());
  }

  @Transactional
  public void delete(UUID id) {
    findById(id);
    userRepository.deleteById(id);
  }
}

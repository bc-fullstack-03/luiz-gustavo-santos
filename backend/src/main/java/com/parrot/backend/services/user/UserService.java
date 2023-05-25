package com.parrot.backend.services.user;

import com.parrot.backend.api.exceptions.NotFoundException;
import com.parrot.backend.api.exceptions.UserAlreadyExistsException;
import com.parrot.backend.data.IUserRepository;
import com.parrot.backend.data.model.Friend;
import com.parrot.backend.entities.User;
import com.parrot.backend.services.fileUpload.IFileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public class UserService implements IUserService {
  @Autowired
  private IUserRepository userRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private IFileUploadService fileUploadService;

  @Transactional
  public void create(CreateUserRequest request) {
    if (userRepository.findUserByEmail(request.email).isPresent()) {
      throw new UserAlreadyExistsException("User already exists");
    }

    var user = new User(request.name, request.email);
    var passwordHash = passwordEncoder.encode(request.password);

    user.setPassword(passwordHash);
    userRepository.save(user);
  }

  public UserResponse findByEmail(String email) {
    var user = getUser(email);

    return new UserResponse(user);
  }

  public UserResponse findById(UUID id) {
    var user = getUserById(id);

    return new UserResponse(user);
  }

  public List<UserResponse> findAll() {
    return userRepository.findAll().stream().map(UserResponse::new).toList();
  }

  @Transactional
  public UserResponse update(UUID id, UpdateUserRequest request) {
    var user = getUserById(id);
    if (request.getName() != null) user.setName(request.name);
    if (request.getPassword() != null) user.setPassword(request.password);

    userRepository.save(user);

    return new UserResponse(user);
  }

  @Transactional
  public void delete(UUID id) {
    getUserById(id);
    userRepository.deleteById(id);
  }

  @Transactional
  public void uploadPhoto(MultipartFile photo) throws Exception {
    var user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    var photoUrl = "";

    try {
      var fileName = user.getId() + "." + photo.getOriginalFilename().substring(photo.getOriginalFilename().lastIndexOf(".") + 1);
      photoUrl = fileUploadService.upload(photo, fileName);
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
    user.setPhotoUrl(photoUrl);
    userRepository.save(user);
  }

  @Transactional
  public void follow(UUID id) {

    var user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    var userToFollow = getUserById(id);

    if (user.getFollowing().contains(userToFollow.getId())) {
      throw new UserAlreadyExistsException("You are already following this user");
    }

    userToFollow.getFollowers().add(user.getId());
    user.getFollowing().add(userToFollow.getId());

    userRepository.save(userToFollow);
    userRepository.save(user);
  }

  @Transactional
  public void unFollow(UUID id) {

    var user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    var userToUnFollow = getUserById(id);

    if (!user.getFollowing().contains(userToUnFollow.getId())) {
      throw new NotFoundException("User not found");
    }

    userToUnFollow.getFollowers().remove(user.getId());
    user.getFollowing().remove(userToUnFollow.getId());

    userRepository.save(userToUnFollow);
    userRepository.save(user);
  }

  public List<Friend> findAllFollowers(UUID userId) {
    var user = getUserById(userId);

    return userRepository
        .findAllById(user.getFollowers())
        .stream().map(Friend::new)
        .toList();
  }

  public List<Friend> findAllFollowings(UUID userId) {
    var user = getUserById(userId);

    return userRepository
        .findAllById(user.getFollowing())
        .stream().map(Friend::new)
        .toList();
  }

  public User getUser(String email) {
    return userRepository.findUserByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));
  }

  public User getUserById(UUID id) {
    return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
  }
}

package com.parrot.backend.api;

import com.parrot.backend.data.model.Friend;
import com.parrot.backend.services.user.CreateUserRequest;
import com.parrot.backend.services.user.UserResponse;
import com.parrot.backend.services.user.IUserService;
import com.parrot.backend.services.user.UpdateUserRequest;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
  @Autowired
  private IUserService userService;

  @Operation(summary = "Create an user")
  @PostMapping("/create")
  public ResponseEntity<String> create(@RequestBody @Valid CreateUserRequest request) {
    userService.create(request);

    return ResponseEntity.status(HttpStatus.CREATED).body("");
  }

  @Operation(summary = "Find an user by email")
  @GetMapping
  public ResponseEntity<UserResponse> findByEmail(String email) {
    return ResponseEntity.ok().body(userService.findByEmail(email));
  }

  @Operation(summary = "Find an user by id")
  @GetMapping("/{id}")
  public ResponseEntity<UserResponse> finById(@PathVariable UUID id) {
    return ResponseEntity.ok().body(userService.findById(id));
  }

  @Operation(summary = "Find all users")
  @GetMapping("/all")
  public ResponseEntity<List<UserResponse>> findAll() {
    return ResponseEntity.ok().body(userService.findAll());
  }

  @Operation(summary = "Update an user")
  @PutMapping("/{id}")
  public ResponseEntity<UserResponse> update(@PathVariable UUID id, @RequestBody @Valid UpdateUserRequest request) {
    return ResponseEntity.ok().body(userService.update(id, request));
  }

  @Operation(summary = "Delete an user")
  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(@PathVariable UUID id) {
    userService.delete(id);

    return ResponseEntity.ok().body("");
  }

  @Operation(summary = "Upload user photo")
  @PostMapping("/photo/upload")
  public ResponseEntity<String> uploadPhoto(@RequestParam("photo") MultipartFile photo) {
    try {
      userService.uploadPhoto(photo);
      return ResponseEntity.ok().body("");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @Operation(summary = "Follow an user")
  @PostMapping("/follow/{id}")
  public ResponseEntity<String>follow(@PathVariable UUID id) {
    userService.follow(id);
    return ResponseEntity.status(HttpStatus.CREATED).body("");
  }

  @Operation(summary = "Unfollow an user")
  @DeleteMapping("/unfollow/{id}")
  public ResponseEntity<String> unFollow(@PathVariable UUID id) {
    userService.unFollow(id);
    return ResponseEntity.ok().body("");
  }

  @Operation(summary = "Find all user followers")
  @GetMapping("/followers/{userId}")
  public ResponseEntity<List<Friend>> findAllFollowers(@PathVariable UUID userId) {
    var response = userService.findAllFollowers(userId);
    return ResponseEntity.ok().body(response);
  }

  @Operation(summary = "Find all user followings")
  @GetMapping("/followings/{userId}")
  public ResponseEntity<List<Friend>> findAllFollowings(@PathVariable UUID userId) {
    var response = userService.findAllFollowings(userId);
    return ResponseEntity.ok().body(response);
  }
}

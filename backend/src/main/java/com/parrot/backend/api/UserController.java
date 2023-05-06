package com.parrot.backend.api;

import com.parrot.backend.services.user.CreateUserRequest;
import com.parrot.backend.services.user.UserResponse;
import com.parrot.backend.services.user.IUserService;
import com.parrot.backend.services.user.UpdateUserRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
  @Autowired
  private IUserService userService;

  @PostMapping("/create")
  public ResponseEntity<String> create(@RequestBody @Valid CreateUserRequest request) {
    userService.create(request);

    return ResponseEntity.status(HttpStatus.CREATED).body("");
  }

  @GetMapping
  public ResponseEntity<UserResponse> findByEmail(String email) {
    return ResponseEntity.ok().body(userService.findByEmail(email));
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserResponse> finById(@PathVariable UUID id) {
    return ResponseEntity.ok().body(userService.findById(id));
  }

  @GetMapping("/all")
  public ResponseEntity<List<UserResponse>> findAll() {
    return ResponseEntity.ok().body(userService.findAll());
  }

  @PutMapping("/{id}")
  public ResponseEntity<UserResponse> update(@PathVariable UUID id, @RequestBody @Valid UpdateUserRequest request) {
    return ResponseEntity.ok().body(userService.update(id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(@PathVariable UUID id) {
    userService.delete(id);

    return ResponseEntity.ok().body("");
  }

  @PostMapping("/photo/upload")
  public ResponseEntity<String> uploadPhoto(@RequestParam("id") UUID id, @RequestParam("photo") MultipartFile photo) {
    try {
      userService.uploadPhoto(id, photo);
      return ResponseEntity.ok().body("");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}

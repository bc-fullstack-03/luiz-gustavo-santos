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
    var response = userService.findByEmail(email);

    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserResponse> finById(@PathVariable UUID id) {
    var response = userService.findById(id);

    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/all")
  public ResponseEntity<List<UserResponse>> findAll() {
    return  ResponseEntity.ok().body(userService.findAll());
  }
  @PutMapping("/{id}")
  public ResponseEntity<UserResponse> update(@PathVariable UUID id, @RequestBody @Valid UpdateUserRequest request) {
    var response = userService.update(id, request);

    return ResponseEntity.ok().body(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(@PathVariable UUID id) {
    userService.delete(id);

    return ResponseEntity.ok().body("");
  }
}

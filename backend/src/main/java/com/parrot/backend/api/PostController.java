package com.parrot.backend.api;

import com.parrot.backend.services.post.IPostService;
import com.parrot.backend.services.post.PostResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {

  @Autowired
  private IPostService postService;

  @PostMapping
  public ResponseEntity<String> create(
      @RequestParam("content") @Valid String content,
      @RequestParam(value = "photo", required = false) MultipartFile photo
  ) {
    try {
      postService.create(content, photo);
      return ResponseEntity.status(HttpStatus.CREATED).body("");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping
  public ResponseEntity<List<PostResponse>> findAll() {
    return ResponseEntity.ok().body(postService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<PostResponse> findById(@PathVariable UUID id) {
    return ResponseEntity.ok().body(postService.findById(id));
  }

  @GetMapping("/user/{userId}")
  public ResponseEntity<List<PostResponse>> findByUserId(@PathVariable UUID userId) {
    return ResponseEntity.ok().body(postService.findByUserId(userId));
  }

  @PutMapping("/{id}")
  public ResponseEntity<PostResponse> update(
      @PathVariable UUID id,
      @RequestParam(value = "content", required = false) String content,
      @RequestParam(value = "photo", required = false) MultipartFile photo
  ) throws Exception {
    try {
      return ResponseEntity.ok().body(postService.update(id, content, photo));
    } catch (Exception e) {
      throw new Exception(e);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(@PathVariable UUID id) {
    postService.delete(id);
    return ResponseEntity.ok().body("");
  }
}

package com.parrot.backend.api;

import com.parrot.backend.data.model.PaginationResponse;
import com.parrot.backend.services.post.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

  @Operation(summary = "Create a post")
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

  @Operation(summary = "Find all posts")
  @GetMapping
  public ResponseEntity<PaginationResponse<PostResponse>> findAll(Pageable page) {
    return ResponseEntity.ok().body(postService.findAll(page));
  }

  @Operation(summary = "Find post by id")
  @GetMapping("/{id}")
  public ResponseEntity<PostResponse> findById(@PathVariable UUID id) {
    return ResponseEntity.ok().body(postService.findById(id));
  }

  @Operation(summary = "Find all user posts")
  @GetMapping("/user/{userId}")
  public ResponseEntity<List<PostResponse>> findByUserId(@PathVariable UUID userId) {
    return ResponseEntity.ok().body(postService.findByUserId(userId));
  }

  @Operation(summary = "Update a post")
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

  @Operation(summary = "Delete a post")
  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(@PathVariable UUID id) {
    postService.delete(id);
    return ResponseEntity.ok().body("");
  }

  @Operation(summary = "Create a comment")
  @PostMapping("/comment")
  public ResponseEntity<CommentResponse>createComment(@RequestBody @Valid CreateCommentRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(postService.createComment(request));
  }

  @Operation(summary = "Find all comments by post")
  @GetMapping("/comment")
  public ResponseEntity<List<CommentResponse>>findAllCommentsByPost(@RequestParam(value = "postId") UUID postId) {
    return ResponseEntity.ok().body(postService.findAllCommentsByPost(postId));
  }

  @Operation(summary = "Delete a comment")
  @DeleteMapping("/comment/{id}")
  public ResponseEntity<String>deleteComment(
      @PathVariable UUID id,
      @RequestBody @Valid DeleteCommentRequest request
  ) {
    postService.deleteComment(id, request);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Like and dislike a post")
  @PostMapping("/like")
  public ResponseEntity<String>setLike(@RequestBody @Valid LikeRequest request) {
    postService.setLike(request);
    return ResponseEntity.ok().body("");
  }
}

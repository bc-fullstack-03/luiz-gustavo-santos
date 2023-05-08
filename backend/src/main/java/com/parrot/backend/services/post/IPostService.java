package com.parrot.backend.services.post;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface IPostService {
  void create(String content, MultipartFile photo) throws Exception;
  List<PostResponse> findAll();
  PostResponse findById(UUID id);
  List<PostResponse> findByUserId(UUID id);
  PostResponse update(UUID id, String content, MultipartFile photo) throws Exception;
  void delete(UUID id);
  CommentResponse createComment(CreateCommentRequest request);
  List<CommentResponse> findAllCommentsByPost(UUID postId);
  void deleteComment(UUID id, DeleteCommentRequest request);
}

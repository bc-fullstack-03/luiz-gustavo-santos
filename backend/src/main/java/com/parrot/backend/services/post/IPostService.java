package com.parrot.backend.services.post;

import com.parrot.backend.data.model.PaginationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface IPostService {
  void create(String content, MultipartFile photo) throws Exception;
  PaginationResponse<PostResponse> findAll(Pageable page);
  PostResponse findById(UUID id);
  List<PostResponse> findByUserId(UUID id);
  PostResponse update(UUID id, String content, MultipartFile photo) throws Exception;
  void delete(UUID id);
  CommentResponse createComment(CreateCommentRequest request);
  List<CommentResponse> findAllCommentsByPost(UUID postId);
  void deleteComment(UUID id, DeleteCommentRequest request);
  void setLike(LikeRequest request);
}

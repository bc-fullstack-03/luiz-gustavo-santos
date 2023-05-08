package com.parrot.backend.services.post;

import com.amazonaws.services.mq.model.ForbiddenException;
import com.parrot.backend.api.exceptions.NotFoundException;
import com.parrot.backend.data.IPostRepository;
import com.parrot.backend.data.model.Comment;
import com.parrot.backend.entities.Post;
import com.parrot.backend.entities.User;
import com.parrot.backend.services.fileUpload.IFileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class PostService implements IPostService {

  @Autowired
  private IPostRepository postRepository;
  @Autowired
  private IFileUploadService fileUploadService;

  @Transactional
  public void create(String content, MultipartFile photo) throws Exception {
    var user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    var post = new Post(content);

    if(photo != null) {
      var photoUrl = upload(photo, post.getId());
      post.setImage(photoUrl);
    }

    post.setUserId(user.getId());
    postRepository.save(post);
  }

  public List<PostResponse> findAll() {
    return postRepository.findAllPosts();
  }

  public PostResponse findById(UUID id) {
    var post = postRepository.findById(id).orElseThrow(() -> new NotFoundException("Post not found"));

    return new PostResponse(post);
  }

  public List<PostResponse> findByUserId(UUID id) {
    return postRepository.findByUserId(id);
  }

  @Transactional
  public PostResponse update(UUID id, String content, MultipartFile photo) throws Exception {
    var user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    var post = postRepository.findById(id).orElseThrow(() -> new NotFoundException("Post not Found"));

    if(!Objects.equals(post.getUserId(), user.getId())) {
      throw new ForbiddenException("You don't have permission to update this post");
    }

    if(content != null) {
      post.setContent(content);
    }

    if(photo != null) {
      var photoUrl = upload(photo, post.getId());
      post.setImage(photoUrl);
    }
    postRepository.save(post);

    return new PostResponse(post);
  }

  @Transactional
  public void delete(UUID id) {
    var user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    var post = postRepository.findById(id).orElseThrow(() -> new NotFoundException("Post not Found"));

    if(!Objects.equals(post.getUserId(), user.getId())) {
      throw new ForbiddenException("You don't have permission to delete this post");
    }
    postRepository.delete(post);
  }

  @Transactional
  public CommentResponse createComment(CreateCommentRequest request) {
    var user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    var post = postRepository.findById(request.postId).orElseThrow(() -> new NotFoundException("Post not found"));

    if(post.getComments() == null) {
      post.setComments(new ArrayList<>());
    }
    var comment = new Comment(request.comment, user.getId());

    post.getComments().add(comment);
    postRepository.save(post);

    return new CommentResponse(comment);
  }

  public List<CommentResponse> findAllCommentsByPost(UUID postId) {
    var post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("Post not found"));

    return post.getComments().stream().map(CommentResponse::new).toList();
  }

  @Transactional
  public void deleteComment(UUID id, DeleteCommentRequest request) {
    var user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    var post = postRepository.findById(request.postId).orElseThrow(() -> new NotFoundException("Post not found"));

    var comment = post
        .getComments()
        .stream()
        .filter(item -> item.getId().equals(id))
        .findAny()
        .orElseThrow(() -> new NotFoundException("Comment not found"));

    if(!Objects.equals(user.getId(), comment.userId)) {
      throw new ForbiddenException("You don't have permission to delete this comment");
    }

    post.getComments().remove(comment);
    postRepository.save(post);
  }

  public void setLike(LikeRequest request) {
    var user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    var post = postRepository.findById(request.postId).orElseThrow(() -> new NotFoundException("Post not found"));

    if(post.getLikes() == null) {
      post.setLikes(new ArrayList<>());
    }

    if(post.getLikes().contains(user.getId())) {
      post.getLikes().remove(user.getId());
    } else {
      post.getLikes().add(user.getId());
    }

    postRepository.save(post);
  }

  private String upload (MultipartFile photo, UUID postId) throws Exception {
    var photoUrl = "";

    try {
      var fileName = postId + "." + photo
          .getOriginalFilename()
          .substring(photo.getOriginalFilename().lastIndexOf(".") + 1);
      photoUrl = fileUploadService.upload(photo, fileName);
    } catch (Exception e) {
      throw new Exception(e);
    }

    return photoUrl;
  }
}

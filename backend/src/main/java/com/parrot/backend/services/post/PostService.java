package com.parrot.backend.services.post;

import com.amazonaws.services.mq.model.ForbiddenException;
import com.parrot.backend.api.exceptions.NotFoundException;
import com.parrot.backend.data.IPostRepository;
import com.parrot.backend.entities.Post;
import com.parrot.backend.entities.User;
import com.parrot.backend.services.fileUpload.IFileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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

    return new PostResponse(post.getId(), post.getContent(), post.getImage(), post.getUserId());
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

    return new PostResponse(post.getId(), post.getContent(), post.getImage(), post.getUserId());
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

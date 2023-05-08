package com.parrot.backend.data;

import com.parrot.backend.entities.Post;
import com.parrot.backend.services.post.PostResponse;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IPostRepository extends MongoRepository<Post, UUID> {
  @Query("{}")
  List<PostResponse> findAllPosts();

  @Query("{'userId': ?0}")
  List<PostResponse> findByUserId(UUID id);
}

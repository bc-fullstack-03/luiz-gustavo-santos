package com.parrot.backend.data;

import com.parrot.backend.entities.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.UUID;

public interface IPostRepository extends MongoRepository<Post, UUID> {
  @Query("{'userId': ?0}")
  List<Post> findByUserId(UUID id);
}

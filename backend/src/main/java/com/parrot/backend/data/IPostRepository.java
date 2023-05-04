package com.parrot.backend.data;

import com.parrot.backend.entities.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface IPostRepository extends MongoRepository<Post, UUID> {
}

package com.parrot.backend.data;

import com.parrot.backend.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface IUserRepository extends MongoRepository<User, UUID> {
}

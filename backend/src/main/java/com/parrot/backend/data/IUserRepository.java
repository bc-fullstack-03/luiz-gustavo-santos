package com.parrot.backend.data;

import com.parrot.backend.entities.User;
import com.parrot.backend.services.user.UserResponse;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserRepository extends MongoRepository<User, UUID> {
  Optional<User> findUserByEmail(String email);
  @Query("{} {password: 0}")
  List<UserResponse> findAllUsers();
}

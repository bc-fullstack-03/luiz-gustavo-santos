package com.parrot.backend.services.security;

import java.util.UUID;

public interface IJwtService {
  String generateToken(UUID email);
  boolean isValidToken(String token, String userId);
}

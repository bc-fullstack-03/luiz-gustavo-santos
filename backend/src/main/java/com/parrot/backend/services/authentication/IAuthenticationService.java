package com.parrot.backend.services.authentication;

public interface IAuthenticationService {
  AuthenticateResponse authenticate(AuthenticateRequest request) throws Exception;
}

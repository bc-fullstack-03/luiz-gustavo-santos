package com.parrot.backend.services.authentication;

import com.parrot.backend.services.security.IJwtService;
import com.parrot.backend.services.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements IAuthenticationService {

  @Autowired
  private IUserService userService;
  @Autowired
  private IJwtService jwtService;
  @Autowired
  private PasswordEncoder passwordEncoder;

  public AuthenticateResponse authenticate(AuthenticateRequest request) throws Exception {
    var user = userService.getUser(request.email);
    var response = new AuthenticateResponse();
    var token = jwtService.generateToken(user.getId());

    if(user == null) {
      throw new Exception("Invalid credentials");
    }

    if(!passwordEncoder.matches(request.password, user.getPassword())) {
      throw new Exception("Invalid credentials");
    }

    response.setId(user.getId());
    response.setToken(token);

    return response;
  }
}

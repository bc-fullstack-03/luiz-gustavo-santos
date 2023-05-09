package com.parrot.backend.api;

import com.parrot.backend.services.authentication.AuthenticateRequest;
import com.parrot.backend.services.authentication.AuthenticateResponse;
import com.parrot.backend.services.authentication.IAuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/authentication")
public class AuthenticationController {

  @Autowired
  private IAuthenticationService authenticationService;

  @Operation(summary = "Login")
  @PostMapping("/login")
  public ResponseEntity<AuthenticateResponse> authenticate(@RequestBody AuthenticateRequest request) {
    try {
      return ResponseEntity.ok().body(authenticationService.authenticate(request));
    } catch (Exception e) {
      return new ResponseEntity(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }
  }
}

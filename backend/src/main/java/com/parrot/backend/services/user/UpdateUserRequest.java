package com.parrot.backend.services.user;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserRequest {
  @Size(min = 2, max = 50, message = "The name must have a minimum of 2 and a maximum of 50 characters")
  @Pattern(regexp = "^[a-zA-Z ]+$", message = "This field cannot be empty")
  public String name;

  @Size(min = 6, max = 25, message = "Password must have a minimum of 8 and a maximum of 25 characters")
  public String password;
}

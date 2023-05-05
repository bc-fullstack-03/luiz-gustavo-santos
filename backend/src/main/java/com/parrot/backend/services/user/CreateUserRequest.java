package com.parrot.backend.services.user;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateUserRequest {
  @NotBlank(message = "This field cannot be empty")
  @Size(min = 2, max = 50, message = "The name must have a minimum of 2 and a maximum of 50 characters")
  @Pattern(regexp = "^[a-zA-Z ]+$", message = "The name must contain only letters")
  public String name;

  @NotBlank(message = "This field cannot be empty")
  @Email(message = "Invalid email")
  @Pattern(regexp = ".+@.+\\.[a-z]+", message = "Email format invalid")
  public String email;

  @NotBlank(message = "This field cannot be empty")
  @Size(min = 6, max = 25, message = "Password must have a minimum of 6 and a maximum of 25 characters")
  public String password;
}

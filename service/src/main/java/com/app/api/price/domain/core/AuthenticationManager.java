package com.app.api.price.domain.core;

import java.util.Objects;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationManager {

  private final PasswordEncoder  passwordEncoder;

  public AuthenticationManager(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  public Authentication login(Authentication authentication){
    String username = authentication.getName();
    String password = authentication.getCredentials().toString();
    if(!Objects.equals(username, "user")){
      throw new BadCredentialsException("Bad credentials...");
    }
    //if (!passwordEncoder.matches(password,"user")){ with DB we can encrypt when we register an user
    if (!password.equals("user")){
      throw new BadCredentialsException("Bad credentials...");
    }
    return authentication;
  }
}

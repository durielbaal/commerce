package com.app.api.price.rest;

import static com.app.api.shared.rest.Routing.PRICE_PATH;

import com.app.api.price.configuration.JwtService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class AuthController {

  private final JwtService jwtService;

  public AuthController(JwtService jwtService) {
    this.jwtService = jwtService;
  }

  @PostMapping("/auth/login")
  public String login() {
    return jwtService.generateToken(new UsernamePasswordAuthenticationToken("issa","okay"));
  }

}

package com.app.api.price.rest;

import static com.app.api.shared.rest.Routing.GET_LOGIN_PATH;
import static com.app.api.shared.rest.Routing.SECURITY_PATH;

import com.app.api.price.domain.core.AuthenticationManager;
import com.app.api.price.domain.core.JwtService;
import com.app.api.price.domain.model.LoginRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(SECURITY_PATH)
public class AuthController {

  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthController(JwtService jwtService, AuthenticationManager authenticationManager) {
    this.jwtService = jwtService;
    this.authenticationManager = authenticationManager;
  }

  @PostMapping(GET_LOGIN_PATH)
  public ResponseEntity<Map<String,String>> login(@RequestBody LoginRequest user) {
    Map<String, String> response = new HashMap<>();
    List<GrantedAuthority> authorities = List
        .of(new SimpleGrantedAuthority("ROLE_ADMIN"),
            new SimpleGrantedAuthority("ROLE_USER"));
    try{
      Authentication authentication = authenticationManager
          .login(new UsernamePasswordAuthenticationToken(
              user.getUsername(),
              user.getPassword(),
              authorities));
      response.put("token: ",jwtService.generateToken(authentication));
      return ResponseEntity.ok(response);
    }
    catch (BadCredentialsException e){
      response.put("token: ", e.getMessage());
      return ResponseEntity.badRequest().body(response);
    }
    catch (Exception e){
      response.put("token: ", "Something was wrong...");
      return ResponseEntity.badRequest().body(response);
    }


  }

}

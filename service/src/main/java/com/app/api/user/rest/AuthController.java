package com.app.api.user.rest;

import static com.app.api.user.rest.adapter.UserFilterAdapter.adapt;
import static com.app.shared.rest.Routing.GET_LOGIN_PATH;
import static com.app.shared.rest.Routing.SECURITY_PATH;

import com.app.api.user.domain.model.User;
import com.app.api.user.domain.ports.inbound.GetUserFilterUseCase;
import com.app.api.user.infrastructure.security.JwtService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(SECURITY_PATH)
public class AuthController {

  private final GetUserFilterUseCase getUserFilterUseCase;
  private final JwtService jwtService;
  public AuthController(GetUserFilterUseCase getUserFilterUseCase, JwtService jwtService) {
    this.getUserFilterUseCase = getUserFilterUseCase;
    this.jwtService = jwtService;
  }

  @PostMapping(GET_LOGIN_PATH)
  public ResponseEntity<Map<String,String>> login(@RequestBody User user) {
      HashMap<String, String> response = new HashMap<>();
        User userObtained = getUserFilterUseCase.execute(adapt(user.getUsername(),user.getPassword())).block();
        if(userObtained == null) {
          response.put("Token: ", "Bad credentials...");
          return ResponseEntity.badRequest().body(response);
        }
        else response.put("Token: ",jwtService.generateToken(new UsernamePasswordAuthenticationToken(
            userObtained.getUsername(),userObtained.getPassword(),List
            .of(new SimpleGrantedAuthority("ROLE_USER")))));
        return ResponseEntity.ok(response);



  }

}

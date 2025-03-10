package com.app.api.user.rest;

import static com.app.api.user.rest.adapter.UserFilterAdapter.adapt;
import static com.app.shared.rest.Routing.GET_LOGIN_PATH;
import static com.app.shared.rest.Routing.SECURITY_PATH;

import com.app.api.user.domain.model.User;
import com.app.api.user.domain.ports.inbound.GetUserFilterUseCase;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(SECURITY_PATH)
public class AuthController {

  private final GetUserFilterUseCase getUserFilterUseCase;

  public AuthController(GetUserFilterUseCase getUserFilterUseCase) {
    this.getUserFilterUseCase = getUserFilterUseCase;
  }

  @PostMapping(GET_LOGIN_PATH)
  public Mono<String> login(@RequestBody User user) {
    return getUserFilterUseCase.execute(adapt(user.getUsername(),user.getPassword()));
  }

}

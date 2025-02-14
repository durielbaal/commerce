package com.app.api.user.domain.core;

import com.app.api.user.domain.model.UserFilter;
import com.app.api.user.domain.ports.inbound.GetUserFilterUseCase;
import com.app.api.user.domain.ports.outbound.UserPersistancePort;
import com.app.shared.infrastructure.security.JwtService;
import java.util.Collections;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GetUserFilterUseCaseImpl implements GetUserFilterUseCase {

  private  final UserPersistancePort userPersistancePort;
  private final JwtService jwtService;

  public GetUserFilterUseCaseImpl(UserPersistancePort userPersistancePort, JwtService jwtService) {
    this.userPersistancePort = userPersistancePort;
    this.jwtService = jwtService;
  }

  @Override
  public Mono<String> execute(UserFilter filter) {
    return userPersistancePort.getUserToLogin(filter)
        .switchIfEmpty(Mono.error(new BadCredentialsException("Bad credentials...")))
        .map(u -> {
          String role = (u.getRole() != null && !u.getRole().trim().isEmpty()) ? u.getRole() : "ROLE_USER";
          GrantedAuthority authority = new SimpleGrantedAuthority(role);
          UsernamePasswordAuthenticationToken authToken =
              new UsernamePasswordAuthenticationToken(u.getUsername(), u.getPassword(), Collections.singletonList(authority));
          return jwtService.generateToken(authToken);
        });
  }
}

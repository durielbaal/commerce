package com.app.api.user.domain.core;

import com.app.api.user.domain.model.UserFilter;
import com.app.api.user.domain.ports.inbound.GetUserFilterUseCase;
import com.app.api.user.domain.ports.outbound.UserPersistancePort;
import com.app.shared.infrastructure.security.JwtService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import java.util.Collections;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Implementation of the use case for filtering users and generating a JWT token if credentials are correct.
 */
@Service
public class GetUserFilterUseCaseImpl implements GetUserFilterUseCase {

  private final UserPersistancePort userPersistancePort;
  private final JwtService jwtService;

  /**
   * Constructor to inject necessary dependencies.
   *
   * @param userPersistancePort Persistence port to retrieve user information.
   * @param jwtService Service to generate JWT tokens.
   */
  public GetUserFilterUseCaseImpl(UserPersistancePort userPersistancePort, JwtService jwtService) {
    this.userPersistancePort = userPersistancePort;
    this.jwtService = jwtService;
  }

  /**
   * Executes authentication logic, validating the user and generating a JWT token.
   * Applies a Circuit Breaker and Rate Limiter to enhance system resilience.
   *
   * @param filter User filter with credentials for authentication.
   * @return Mono<String> emitting the JWT token if authentication is successful.
   */
  @Override
  @CircuitBreaker(name = "GetUserFilterUseCaseImpl", fallbackMethod = "fallbackUser")
  @RateLimiter(name = "GetUserFilterUseCaseImpl")
  public Mono<String> execute(UserFilter filter) {
    return userPersistancePort.getUserToLogin(filter)
        .switchIfEmpty(Mono.error(new BadCredentialsException("Bad credentials...")))
        .map(u -> {
          // Retrieve the user's role, defaulting to "ROLE_USER" if empty
          String role = (u.getRole() != null && !u.getRole().trim().isEmpty()) ? u.getRole() : "ROLE_USER";
          GrantedAuthority authority = new SimpleGrantedAuthority(role);
          UsernamePasswordAuthenticationToken authToken =
              new UsernamePasswordAuthenticationToken(u.getUsername(), u.getPassword(), Collections.singletonList(authority));
          return jwtService.generateToken(authToken);
        });
  }

  /**
   * Fallback method in case the Circuit Breaker is activated.
   *
   * @param filter User filter for the attempted authentication.
   * @param t Exception that triggered the Circuit Breaker activation.
   * @return Mono<String> with an error message indicating Circuit Breaker activation.
   */
  private Mono<String> fallbackUser(UserFilter filter, Throwable t) {
    return Mono.error(new RuntimeException("Circuit Breaker Activated: " + t.getMessage()));
  }
}

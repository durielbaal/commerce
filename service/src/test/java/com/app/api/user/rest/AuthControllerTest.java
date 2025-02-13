package com.app.api.user.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.app.api.user.domain.model.User;
import com.app.api.user.domain.model.UserFilter;
import com.app.api.user.domain.ports.inbound.GetUserFilterUseCase;
import com.app.shared.infrastructure.security.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.BadCredentialsException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class AuthControllerTest {

  @Mock
  private GetUserFilterUseCase getUserFilterUseCase;

  @Mock
  private JwtService jwtService;

  @InjectMocks
  private AuthController authController;

  private User testUser;
  private String secretKey = "f87b7cfcf5d1b0e1f48c3c62f5f271b62f43a40f82b7c2cccb9b58bfe58f9c50567e3e9638b6fd7e2da9c24584ba00e6";

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    testUser = new User("user", "user");
  }

  private String generateJwtToken(String username) {
    return Jwts.builder()
        .setSubject(username)
        .signWith(SignatureAlgorithm.HS256, secretKey)
        .compact();
  }

  @Test
  void shouldGenerateValidToken() {

    String username = "user";
    String token = generateJwtToken(username);
    when(getUserFilterUseCase.execute(any(UserFilter.class)))
        .thenReturn(Mono.just(token));
    Mono<String> loginResponse = authController.login(testUser);
    StepVerifier.create(loginResponse)
        .expectNextMatches(receivedToken -> {
          try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(receivedToken);
            return true;
          } catch (Exception e) {
            return false;
          }
        })
        .verifyComplete();

    verify(getUserFilterUseCase, times(1)).execute(any(UserFilter.class));
  }

  @Test
  void shouldReturnBadCredentialsWhenUserNotFound() {

    when(getUserFilterUseCase.execute(any(UserFilter.class)))
        .thenReturn(Mono.error(new BadCredentialsException("Bad credentials...")));
    Mono<String> loginResponse = authController.login(testUser);
    StepVerifier.create(loginResponse)
        .expectError(BadCredentialsException.class)
        .verify();
    verify(getUserFilterUseCase, times(1)).execute(any(UserFilter.class));
  }

  @Test
  void shouldMockJwtGeneration() {
    String token = generateJwtToken("user");
    when(jwtService.generateToken(any()))
        .thenReturn(token);
    String generatedToken = jwtService.generateToken(null);
    assert generatedToken.equals(token);
    verify(jwtService, times(1)).generateToken(any());
  }
}

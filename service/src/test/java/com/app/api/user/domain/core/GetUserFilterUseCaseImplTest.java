package com.app.api.user.domain.core;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.app.api.user.domain.model.UserFilter;
import com.app.api.user.domain.ports.outbound.UserPersistancePort;
import com.app.api.user.infrastructure.repository.postgres.entity.UserEntity;
import com.app.shared.infrastructure.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class GetUserFilterUseCaseImplTest {

  @Mock
  private UserPersistancePort userPersistancePort;

  @Mock
  private JwtService jwtService;

  @InjectMocks
  private GetUserFilterUseCaseImpl getUserFilterUseCase;

  private UserFilter userFilter;


  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    userFilter = new UserFilter("user", "user");
  }

  @Test
  void shouldGenerateValidTokenWhenUserFound() {
    UserEntity testUserEntity = Mockito.mock(UserEntity.class);
    when(userPersistancePort.getUserToLogin(any(UserFilter.class)))
        .thenReturn(Mono.just(testUserEntity));
    String token = "f87b7cfcf5d1b0e1f48c3c62f5f271b62f43a40f82b7c2cccb9b58bfe58f9c50567e3e9638b6fd7e2da9c24584ba00e6";
    when(jwtService.generateToken(any(UsernamePasswordAuthenticationToken.class)))
        .thenReturn(token);
    Mono<String> response = getUserFilterUseCase.execute(userFilter);
    StepVerifier.create(response)
        .expectNext(token)
        .verifyComplete();
    verify(userPersistancePort).getUserToLogin(any(UserFilter.class));
    verify(jwtService).generateToken(any(UsernamePasswordAuthenticationToken.class));
  }

  @Test
  void shouldReturnBadCredentialsWhenUserNotFound() {
    when(userPersistancePort.getUserToLogin(any(UserFilter.class)))
        .thenReturn(Mono.empty());
    Mono<String> response = getUserFilterUseCase.execute(userFilter);
    StepVerifier.create(response)
        .expectError(BadCredentialsException.class)
        .verify();
    verify(userPersistancePort).getUserToLogin(any(UserFilter.class));
  }
}


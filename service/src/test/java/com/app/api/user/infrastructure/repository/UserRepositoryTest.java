package com.app.api.user.infrastructure.repository;

import static org.mockito.ArgumentMatchers.any;

import com.app.api.user.domain.model.UserFilter;
import com.app.api.user.infrastructure.repository.postgres.dao.UserDao;
import com.app.api.user.infrastructure.repository.postgres.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {

  @Mock
  private UserDao userDao;

  @InjectMocks
  private UserRepository userRepository;

  @Test
  void shouldReturnUserWhenFilterMatches() {
    UserFilter userFilter = new UserFilter("user", "user");
    UserEntity testUserEntity = Mockito.mock(UserEntity.class);

    Mockito.when(userDao.getUser(any(), any()))
        .thenReturn(Mono.just(testUserEntity));

    Mono<UserEntity> result = userRepository.getUser(userFilter);

    StepVerifier.create(result)
        .expectNext(testUserEntity)
        .verifyComplete();
  }

  @Test
  void shouldReturnEmptyWhenUserNotFound() {
    UserFilter userFilter = new UserFilter("nonexistentUser", "wrongPassword");
    Mockito.when(userDao.getUser(any(), any()))
        .thenReturn(Mono.empty());
    Mono<UserEntity> result = userRepository.getUser(userFilter);
    StepVerifier.create(result)
        .verifyComplete();
  }
}

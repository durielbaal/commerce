package com.app.api.user.infrastructure.repository.postgres.dao;

import com.app.api.user.infrastructure.repository.postgres.entity.UserEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserDao extends ReactiveCrudRepository<UserEntity, Long> {
  @Query("SELECT * FROM Users WHERE username = :username AND password = :password")
  Mono<UserEntity> getUser(String username, String password);
}

package com.app.api.user.infrastructure.repository;

import static com.app.api.user.domain.ports.outbound.UserPersistancePort.GET_USER_TO_LOGIN_ADDRESS;
import static com.app.shared.domain.ports.outbound.OutboundPort.register;

import com.app.api.user.domain.model.UserFilter;
import com.app.api.user.infrastructure.repository.postgres.dao.UserDao;
import com.app.api.user.infrastructure.repository.postgres.entity.UserEntity;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class UserRepository {
  private final UserDao userDao;

  public UserRepository(UserDao userDao) {
    this.userDao = userDao;
  }
  @PostConstruct
  public void start(){register(GET_USER_TO_LOGIN_ADDRESS,this::getUser);}

  public Mono<UserEntity> getUser(UserFilter userFilter){
    return userDao.getUser(userFilter.getUser(), userFilter.getPassword());
  }
}

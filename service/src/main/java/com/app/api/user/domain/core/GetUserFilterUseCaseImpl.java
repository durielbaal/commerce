package com.app.api.user.domain.core;

import com.app.api.user.domain.model.User;
import com.app.api.user.domain.model.UserFilter;
import com.app.api.user.domain.ports.inbound.GetUserFilterUseCase;
import com.app.api.user.domain.ports.outbound.UserPersistancePort;
import com.app.api.user.infrastructure.repository.postgres.entity.UserEntity;
import com.app.api.user.infrastructure.security.JwtService;
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
  public Mono<User> execute(UserFilter filter) {
   return userPersistancePort.getPriceByFilter(filter).map(UserEntity::entityToModel);
  }

}

package com.app.api.user.domain.ports.outbound;

import static com.app.shared.domain.ports.outbound.OutboundPort.requestEvent;

import com.app.api.user.domain.model.UserFilter;
import com.app.api.user.infrastructure.repository.postgres.entity.UserEntity;
import com.app.shared.domain.model.Message;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class UserPersistancePort {
  public static final String GET_USER_BY_FILTER_ADDRESS = "getUserByFilterAddress";

  public UserPersistancePort() {
  }

  public Mono<UserEntity> getPriceByFilter(UserFilter filter){
    return requestEvent(new Message<>(GET_USER_BY_FILTER_ADDRESS, filter));
  }

}

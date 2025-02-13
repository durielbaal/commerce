package com.app.api.user.domain.ports.outbound;

import static org.mockito.ArgumentMatchers.any;

import com.app.api.user.domain.model.UserFilter;
import com.app.api.user.infrastructure.repository.postgres.entity.UserEntity;
import com.app.shared.domain.ports.outbound.OutboundPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class UserPersistancePortTest {

    private UserPersistancePort userPersistancePort;
    private OutboundPort.EventHandler<UserFilter, UserEntity> handler;

    @BeforeEach
    void setUp() {
        handler = Mockito.mock(OutboundPort.EventHandler.class);
        userPersistancePort = new UserPersistancePort();
        OutboundPort.register(UserPersistancePort.GET_USER_TO_LOGIN_ADDRESS, handler);
    }

    @Test
    void shouldReturnUserWhenEventIsDispatched() {
        UserFilter filter = new UserFilter("user", "user");
        UserEntity testUserEntity = Mockito.mock(UserEntity.class);

        Mockito.when(handler.handle(any()))
                .thenReturn(Mono.just(testUserEntity));

        Mono<UserEntity> result = userPersistancePort.getUserToLogin(filter);

        StepVerifier.create(result)
                .expectNext(testUserEntity)
                .verifyComplete();
    }

    @Test
    void shouldInvokeHandlerWhenRegistered() {
        UserFilter filter = new UserFilter("user", "user");
        UserEntity testUserEntity = Mockito.mock(UserEntity.class);
        Mockito.when(handler.handle(any()))
                .thenReturn(Mono.just(testUserEntity));

        Mono<UserEntity> result = userPersistancePort.getUserToLogin(filter);

        StepVerifier.create(result)
                .expectNext(testUserEntity)
                .verifyComplete();

        Mockito.verify(handler, Mockito.times(1)).handle(filter);
    }
}

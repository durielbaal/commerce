package com.app.api.shared.domain.ports.outbound;

import static org.mockito.ArgumentMatchers.any;

import com.app.shared.domain.model.Message;
import com.app.shared.domain.ports.outbound.OutboundPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class OutboundPortTest {

  private OutboundPort.EventHandler<String, String> handler;

  @BeforeEach
  void setUp() {
    handler = Mockito.mock(OutboundPort.EventHandler.class);
    OutboundPort.register("testAddress", handler);
  }

  @Test
  void shouldReturnHandlerResultWhenEventIsDispatched() {
    Message<String> event = new Message<>("testAddress", "testBody");

    Mockito.when(handler.handle(any()))
        .thenReturn(Mono.just("processed"));

    Mono<String> result = OutboundPort.requestEvent(event);

    StepVerifier.create(result)
        .expectNext("processed")
        .verifyComplete();
  }

  @Test
  void shouldReturnErrorWhenNoHandlerIsRegistered() {
    Message<String> event = new Message<>("unknownAddress", "testBody");

    Mono<String> result = OutboundPort.requestEvent(event);

    StepVerifier.create(result)
        .expectError(IllegalArgumentException.class)
        .verify();
  }

  @Test
  void shouldInvokeHandlerWhenRegistered() {
    Message<String> event = new Message<>("testAddress", "testBody");

    Mockito.when(handler.handle(any()))
        .thenReturn(Mono.just("processed"));

    Mono<String> result = OutboundPort.requestEvent(event);

    StepVerifier.create(result)
        .expectNext("processed")
        .verifyComplete();

    Mockito.verify(handler, Mockito.times(1)).handle("testBody");
  }
}

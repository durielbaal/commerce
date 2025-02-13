package com.app.shared.domain.ports.outbound;

import com.app.shared.domain.model.Message;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * OutboundPort acts as an in-memory event bus, allowing components to communicate asynchronously
 * without direct dependencies. It provides methods to register event handlers and dispatch events.
 */
@Component
public class OutboundPort {

  /**
   * A concurrent map storing registered event handlers, where the key is the event address
   * and the value is the corresponding handler.
   */
  private static final Map<String, EventHandler<?, ?>> eventBus = new ConcurrentHashMap<>();

  /**
   * Private constructor to prevent instantiation.
   */
  private OutboundPort() {
  }
  /**
   * Dispatches an event to the corresponding registered handler based on its address.
   *
   * @param event The message containing the event data and the address.
   * @param <B> The type of the request body.
   * @param <R> The type of the response.
   * @return A {@code Mono<R>} containing the result of the event processing, or an error if no handler is found.
   */
  public static <B, R> Mono<R> requestEvent(Message<B> event) {
    EventHandler<B, R> handler = resolve(event.address());
    if (handler == null) {
      return Mono.error(new IllegalArgumentException("No handler found for address: " + event.address()));
    }
    return handler.handle(event.body());
  }

  /**
   * Registers an event handler for a specific address.
   *
   * @param address The unique identifier for the event type.
   * @param handler The event handler responsible for processing events of this type.
   * @param <B> The type of the request body handled by this event.
   * @param <R> The type of the response produced by this handler.
   */
  public static <B, R> void register(String address, EventHandler<B, R> handler) {
    eventBus.put(address, handler);
  }

  /**
   * Retrieves the event handler associated with the given address.
   *
   * @param address The address of the event.
   * @param <B> The type of the request body.
   * @param <R> The type of the response.
   * @return The corresponding event handler, or null if none is found.
   */
  @SuppressWarnings("unchecked")
  private static <B, R> EventHandler<B, R> resolve(String address) {
    return (EventHandler<B, R>) eventBus.get(address);
  }

  /**
   * Functional interface representing an event handler that processes an event asynchronously.
   *
   * @param <B> The type of the request body.
   * @param <R> The type of the response.
   */
  @FunctionalInterface
  public interface EventHandler<B, R> {
    /**
     * Processes an event and returns a reactive Mono containing the result.
     *
     * @param body The request body of the event.
     * @return A {@code Mono<R>} containing the result of the event processing.
     */
    Mono<R> handle(B body);
  }
}

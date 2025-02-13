package com.app.api.price.domain.ports.outbound;

import static com.app.shared.domain.ports.outbound.OutboundPort.requestEvent;

import com.app.api.price.domain.model.PriceFilter;
import com.app.api.price.infrastructure.repository.postgres.entity.PriceEntity;
import com.app.shared.domain.model.Message;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * PricePersistencePort acts as an outbound adapter responsible for interacting with the event bus
 * to fetch price-related data asynchronously.
 */
@Component
public class PricePersistencePort {

  /**
   * Address identifier for fetching price by filter events.
   */
  public static final String GET_PRICE_BY_FILTER_ADDRESS = "getPriceByFilterAddress";

  private PricePersistencePort() {
  }

  /**
   * Requests price data based on the provided filter.
   *
   * @param filter The price filter containing criteria for fetching price data.
   * @return A {@code Mono<PriceEntity>} containing the result of the query.
   */
  public Mono<PriceEntity> getPriceByFilter(PriceFilter filter){
    return requestEvent(new Message<>(GET_PRICE_BY_FILTER_ADDRESS, filter));
  }
}


package com.app.api.price.domain.ports.outbound;

import static com.app.api.shared.domain.ports.outbound.OutboundPort.requestEvent;

import com.app.api.price.domain.model.Price;
import com.app.api.price.domain.model.PriceFilter;
import com.app.api.price.infrastructure.postgres.entity.PriceEntity;
import com.app.api.shared.domain.model.Message;
import com.app.api.shared.domain.ports.outbound.OutboundPort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class PricePersistencePort {

  public static final String GET_PRICE_BY_FILTER_ADDRESS = "getPriceByFilterAddress";

  private PricePersistencePort() {
  }

  public Mono<PriceEntity> getPriceByFilter(PriceFilter filter){
    return requestEvent(new Message<>(GET_PRICE_BY_FILTER_ADDRESS, filter));
  }
}

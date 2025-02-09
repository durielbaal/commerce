package com.app.api.price.domain.core;

import com.app.api.price.domain.model.Price;
import com.app.api.price.domain.model.PriceFilter;
import com.app.api.price.domain.ports.inbound.GetPriceByFilterUseCase;
import com.app.api.price.domain.ports.outbound.PricePersistencePort;
import com.app.api.price.infrastructure.repository.postgres.entity.PriceEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GetPriceByFilterUseCaseImpl implements GetPriceByFilterUseCase {

  private final PricePersistencePort pricePersistencePort;

  public GetPriceByFilterUseCaseImpl(PricePersistencePort pricePersistencePort) {
    this.pricePersistencePort = pricePersistencePort;
  }

  @Override
  public Mono<Price> execute(PriceFilter filter) {
    return pricePersistencePort.getPriceByFilter(filter).map(PriceEntity::entityToModel);
  }
}

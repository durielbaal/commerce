package com.app.api.price.domain.core;

import com.app.api.price.domain.model.Price;
import com.app.api.price.domain.model.PriceFilter;
import com.app.api.price.domain.ports.inbound.GetPriceByFilterUseCase;
import com.app.api.price.domain.ports.outbound.PricePersistencePort;
import com.app.api.price.infrastructure.repository.postgres.entity.PriceEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Implementation of the {@link GetPriceByFilterUseCase} interface.
 * This service handles fetching price data based on a given filter.
 */
@Service
public class GetPriceByFilterUseCaseImpl implements GetPriceByFilterUseCase {

  private final PricePersistencePort pricePersistencePort;

  /**
   * Constructor to initialize the use case with the required persistence port.
   *
   * @param pricePersistencePort The outbound port responsible for retrieving price data.
   */
  public GetPriceByFilterUseCaseImpl(PricePersistencePort pricePersistencePort) {
    this.pricePersistencePort = pricePersistencePort;
  }

  /**
   * Executes the use case to fetch price data based on the given filter.
   *
   * @param filter The filter criteria for retrieving price information.
   * @return A {@code Mono<Price>} containing the retrieved price data.
   */
  @Override
  public Mono<Price> execute(PriceFilter filter) {
    return pricePersistencePort.getPriceByFilter(filter).map(PriceEntity::entityToModel);
  }
}

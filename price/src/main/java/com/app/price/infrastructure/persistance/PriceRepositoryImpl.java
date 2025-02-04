package com.app.price.infrastructure.persistance;

import com.app.price.core.domain.model.Price;
import com.app.price.core.domain.port.output.PriceOutputPort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@RequiredArgsConstructor
public class PriceRepositoryImpl implements PriceOutputPort {


  private final PriceRepository priceRepository;

  /**
   * Finds a price by its ID.
   *
   * @param id The price ID.
   * @return A Mono containing the price, or empty if not found.
   */
  @Override
  public Mono<Price> findById(Long id) {
    return priceRepository.findById(id);
  }

  /**
   * Retrieves all prices.
   *
   * @return A Flux containing all prices.
   */
  @Override
  public Flux<Price> findAll() {
    return priceRepository.findAll();
  }

  /**
   * Saves a price.
   *
   * @param price The price entity.
   * @return A Mono containing the saved price.
   */
  @Override
  public Mono<Price> save(Price price) {
    return priceRepository.save(price);
  }


}

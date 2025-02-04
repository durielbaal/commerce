package com.app.price.core.domain.port.output;

import com.app.price.core.domain.model.Price;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Port for accessing price data from the database.
 * Defines the contract for retrieving prices in a reactive manner.
 */
public interface PriceOutputPort {


  /**
   * Finds a price by its ID.
   * @param id The price ID.
   * @return A Mono containing the price, or empty if not found.
   */
  Mono<Price> findById(Long id);

  /**
   * Retrieves all prices.
   * @return A Flux containing all prices.
   */
  Flux<Price> findAll();

  /**
   * Saves a price.
   * @param price The price entity.
   * @return A Mono containing the saved price.
   */
  Mono<Price> save(Price price);
}


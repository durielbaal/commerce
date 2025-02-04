package com.app.price.core.domain.port.input;

import com.app.price.core.domain.model.Price;
import java.time.LocalDateTime;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PriceInputPort {
  /**
   * Finds the applicable price for a given product, brand, and application date.
   *
   * @param productId  The identifier of the product.
   * @param brandId    The identifier of the brand.
   * @param certainDate    Date of searching.
   * @return A {@link Mono} containing the applicable {@link Price}, or empty if no price is found.
   */
  Mono<Price> findByBrandAndProductAndDate(Integer brandId, Integer productId, LocalDateTime certainDate);
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

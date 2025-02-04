package com.app.price.core.application;

import com.app.price.core.domain.model.Price;
import com.app.price.core.domain.port.input.PriceInputPort;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service that handles business logic related to prices.
 * It delegates the queries to the input port.
 */
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService{
  private final PriceInputPort priceQueryPort;
  /**
   * Finds a price by its ID.
   *
   * @param id The price ID.
   * @return A Mono containing the price, or empty if not found.
   */
  public Mono<Price> findById(Long id) {
    return priceQueryPort.findById(id);
  }

  /**
   * Finds all prices.
   *
   * @return A Flux containing all prices.
   */
  public Flux<Price> findAllPrices() {
    return priceQueryPort.findAll();
  }

  /**
   * Finds the applicable price for a given product, brand, and application date.
   *
   * @param productId  The identifier of the product.
   * @param brandId    The identifier of the brand.
   * @param certainDate The date for which to find the price.
   * @return A Mono containing the applicable price, or empty if no price is found.
   */
  public Mono<Price> findPriceByProductAndBrand(Integer productId, Integer brandId, LocalDateTime certainDate) {
    return priceQueryPort.findByBrandAndProductAndDate(brandId, productId, certainDate);
  }
}

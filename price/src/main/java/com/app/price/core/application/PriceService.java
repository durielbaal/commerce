package com.app.price.core.application;

import com.app.price.core.domain.model.Price;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Interface of Service that handles business logic related to prices.
 * It delegates the queries to the input port.
 */
@Service
public interface PriceService {

  /**
   * Finds a price by its ID.
   *
   * @param id The price ID.
   * @return A Mono containing the price, or empty if not found.
   */
  Mono<Price> findById(Long id);

  /**
   * Finds all prices.
   *
   * @return A Flux containing all prices.
   */
  Flux<Price> findAllPrices();

  /**
   * Finds the applicable price for a given product, brand, and application date.
   *
   * @param productId  The identifier of the product.
   * @param brandId    The identifier of the brand.
   * @param certainDate The date for which to find the price.
   * @return A Mono containing the applicable price, or empty if no price is found.
   */
  Mono<Price> findPriceByProductAndBrand(Integer productId, Integer brandId, LocalDateTime certainDate);
}

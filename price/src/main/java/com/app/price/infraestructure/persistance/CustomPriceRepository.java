package com.app.price.infraestructure.persistance;

import com.app.price.core.domain.model.Price;
import java.time.LocalDateTime;
import reactor.core.publisher.Mono;

public interface CustomPriceRepository {
  Mono<Price> findPriceByBrandAndProductAndDate(Integer brandId, Integer productId, LocalDateTime certainDate);
}

package com.app.api.price.infrastructure.postgres.dao;

import com.app.api.price.infrastructure.postgres.entity.PriceEntity;
import java.time.LocalDateTime;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PriceDao extends ReactiveCrudRepository<PriceEntity, Long> {

  @Query("SELECT * FROM Prices WHERE brand_id = :brandId AND product_id = :productId AND "
      + ":certainDate BETWEEN start_date AND end_date ORDER BY priority DESC LIMIT 1")
  Mono<PriceEntity> getPriceByFilter(Integer brandId,Integer productId, LocalDateTime certainDate);

}

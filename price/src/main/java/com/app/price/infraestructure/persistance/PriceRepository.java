package com.app.price.infraestructure.persistance;

import com.app.price.core.domain.model.Price;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository extends ReactiveCrudRepository<Price, Long>{
}

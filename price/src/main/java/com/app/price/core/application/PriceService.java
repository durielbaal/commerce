package com.app.price.core.application;

import com.app.price.core.domain.model.Price;
import com.app.price.infraestructure.persistance.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PriceService {
  private final  PriceRepository priceRepository;

  public Mono<Price> getPriceById(Long id) {
    return priceRepository.findById(id);
  }
}
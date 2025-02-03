package com.app.price.infraestructure.api;

import com.app.price.core.application.PriceService;
import com.app.price.core.domain.model.Price;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/commerce/business")
public class PriceController {

  private PriceService priceService;

  @GetMapping("/price/{id}")
  public Mono<Price> getPrice(@PathVariable Long id) {
    return priceService.getPriceById(id);
  }
}

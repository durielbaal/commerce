package com.app.api.price.rest;

import static com.app.api.price.rest.adapter.PriceFilterAdapter.adapt;
import static com.app.api.shared.rest.Routing.GET_PRICE_BY_FILTER_PATH;
import static com.app.api.shared.rest.Routing.PRICE_PATH;

import com.app.api.price.domain.model.Price;
import com.app.api.price.domain.ports.inbound.GetPriceByFilterUseCase;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(PRICE_PATH)
public class PriceController {

  private final GetPriceByFilterUseCase getPriceByFilterUseCase;

  public PriceController(GetPriceByFilterUseCase getPriceByFilterUseCase) {
    this.getPriceByFilterUseCase = getPriceByFilterUseCase;
  }

  @GetMapping(GET_PRICE_BY_FILTER_PATH+"/{brandId}/{productId}")
  public Mono<Price> getPriceByFilter(@PathVariable Integer brandId,
      @PathVariable Integer productId, @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss") LocalDateTime certainDate){
    return getPriceByFilterUseCase.execute(adapt(brandId,productId,certainDate));
  }

}

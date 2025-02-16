package com.app.api.price.rest;

import static com.app.api.price.rest.adapter.PriceFilterAdapter.adapt;
import static com.app.shared.rest.Routing.GET_PRICE_BY_FILTER_PATH;
import static com.app.shared.rest.Routing.PRICE_PATH;

import com.app.api.price.domain.model.Price;
import com.app.api.price.domain.model.PriceRequest;
import com.app.api.price.domain.ports.inbound.GetPriceByFilterUseCase;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(PRICE_PATH)
public class PriceController {

  private final GetPriceByFilterUseCase getPriceByFilterUseCase;

  public PriceController(GetPriceByFilterUseCase getPriceByFilterUseCase) {
    this.getPriceByFilterUseCase = getPriceByFilterUseCase;
  }

  @PostMapping(GET_PRICE_BY_FILTER_PATH)
  public Mono<Price> getPriceByFilter(@RequestBody PriceRequest priceRequest){
    return getPriceByFilterUseCase.execute(adapt(priceRequest.getBrandId(),priceRequest.getProductId(),priceRequest.getCertainDate()));
  }

}

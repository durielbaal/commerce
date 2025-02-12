package com.app.api.price.domain.ports.inbound;

import com.app.api.price.domain.model.Price;
import com.app.api.price.domain.model.PriceFilter;
import com.app.shared.domain.ports.inbound.UseCase;
import reactor.core.publisher.Mono;

public interface GetPriceByFilterUseCase extends UseCase<PriceFilter, Mono<Price>> {

}

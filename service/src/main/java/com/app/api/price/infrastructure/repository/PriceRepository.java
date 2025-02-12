package com.app.api.price.infrastructure.repository;

import static com.app.api.price.domain.ports.outbound.PricePersistencePort.GET_PRICE_BY_FILTER_ADDRESS;
import static com.app.shared.domain.ports.outbound.OutboundPort.register;

import com.app.api.price.domain.model.PriceFilter;
import com.app.api.price.infrastructure.repository.postgres.dao.PriceDao;
import com.app.api.price.infrastructure.repository.postgres.entity.PriceEntity;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class PriceRepository {

  private final PriceDao priceDao;

  public PriceRepository(PriceDao priceDao) {
    this.priceDao = priceDao;
  }

  @PostConstruct
  public void start(){
    register(GET_PRICE_BY_FILTER_ADDRESS, this::findByFilter);
  }

  public Mono<PriceEntity> findByFilter(PriceFilter filter){
    return priceDao.getPriceByFilter(filter.getBrandId(), filter.getProductId(), filter.getCertainDate());
  }
}

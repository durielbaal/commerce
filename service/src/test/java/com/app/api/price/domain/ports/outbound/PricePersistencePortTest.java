package com.app.api.price.domain.ports.outbound;


import static org.junit.jupiter.api.Assertions.fail;

import com.app.api.price.domain.model.Price;
import com.app.api.price.domain.model.PriceFilter;
import com.app.api.price.infrastructure.repository.postgres.entity.PriceEntity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;

@SpringBootTest
@TestConstructor(autowireMode = AutowireMode.ALL)
public class PricePersistencePortTest {

  private final PricePersistencePort pricePersistencePort;

  public PricePersistencePortTest(PricePersistencePort pricePersistencePort) {
    this.pricePersistencePort = pricePersistencePort;
  }

  @Test
  void shouldFindPrice() {
    LocalDateTime dateTimeWork = createLocalDateTime("14-06-2020 16:00:00", "dd-MM-yyyy HH:mm:ss");
    Price expectedPrice = new Price(1, 2, 35455, new BigDecimal("25.45"), "EUR");
    PriceFilter priceFilter = new PriceFilter(1, 35455, dateTimeWork);
    assert compareDbResultWithExpected(priceFilter, expectedPrice);
  }

  @Test
  void shouldNotFindPrice() {
    LocalDateTime dateTimeNotWork = createLocalDateTime("14-06-2024 16:00:00", "dd-MM-yyyy HH:mm:ss");
    Price expectedPrice = new Price(1, 2, 35455, new BigDecimal("25.45"), "EUR");
    PriceFilter priceFilter = new PriceFilter(1, 35455, dateTimeNotWork);
    assert !compareDbResultWithExpected(priceFilter, expectedPrice);
  }

  private Boolean compareDbResultWithExpected(PriceFilter priceFilter, Price expectedPrice){
    Price priceDb = pricePersistencePort.getPriceByFilter(priceFilter)
        .doOnError(error -> fail("Unexpected error: " + error.getMessage()))
        .map(PriceEntity::entityToModel)
        .block();
    return expectedPrice.equals(priceDb);
  }

  private LocalDateTime createLocalDateTime(String dateString,String pattern){
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
    return LocalDateTime.parse(dateString,formatter);
  }
}

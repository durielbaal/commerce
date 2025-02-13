package com.app.api.price.infrastructure.repository;

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
public class PriceRepositoryTest {

  private final PriceRepository priceRepository;

  public PriceRepositoryTest(PriceRepository priceRepository) {
    this.priceRepository = priceRepository;
  }

  @Test
  void shouldFindPriceByFilter() {
    LocalDateTime dateTimeWork = createLocalDateTime("14-06-2020 16:00:00", "dd-MM-yyyy HH:mm:ss");
    PriceFilter priceFilter = new PriceFilter(1, 35455, dateTimeWork);
    Price expectedPrice = new Price(1, 2, 35455, new BigDecimal("25.45"), "EUR");
    Price resultPrice = priceRepository.findByFilter(priceFilter)
        .doOnError(error -> fail("Unexpected error: " + error.getMessage()))
        .map(PriceEntity::entityToModel)
        .block();
    assert expectedPrice.equals(resultPrice);
  }

  @Test
  void shouldNotFindPriceByFilter() {
    LocalDateTime dateTimeWork = createLocalDateTime("14-06-2024 16:00:00", "dd-MM-yyyy HH:mm:ss");
    PriceFilter priceFilter = new PriceFilter(1, 35455, dateTimeWork);
    Price expectedPrice = new Price(1, 2, 35455, new BigDecimal("25.45"), "EUR");
    Price resultPrice = priceRepository.findByFilter(priceFilter)
        .doOnError(error -> fail("Unexpected error: " + error.getMessage()))
        .map(PriceEntity::entityToModel)
        .block();
    assert !expectedPrice.equals(resultPrice);
  }

  private LocalDateTime createLocalDateTime(String dateString,String pattern){
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
    return LocalDateTime.parse(dateString,formatter);
  }
}

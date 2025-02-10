package com.app.api.price.infrastructure.repository.postgres.dao;

import static org.junit.jupiter.api.Assertions.fail;

import com.app.api.price.domain.model.Price;
import com.app.api.price.infrastructure.repository.postgres.entity.PriceEntity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PriceDaoTest {
  @Autowired
  private PriceDao priceDao;

  @Test
  void shouldFindPrice() {
    LocalDateTime dateTimeWork = createLocalDateTime("14-06-2020 16:00:00", "dd-MM-yyyy HH:mm:ss");
    Price expectedPrice = new Price(1, 2, 35455, new BigDecimal("25.45"), "EUR");
    boolean itShouldWork = compareDbResultWithExpected(1,35455, dateTimeWork, expectedPrice);
    assert itShouldWork;
  }

  @Test
  void shouldNotFindPrice() {
    LocalDateTime dateTimeNotWork = createLocalDateTime("14-06-2024 16:00:00", "dd-MM-yyyy HH:mm:ss");
    Price expectedPrice = new Price(1, 2, 35455, new BigDecimal("25.45"), "EUR");
    boolean itShouldNotWork = compareDbResultWithExpected(1,35455, dateTimeNotWork, expectedPrice);
    assert !itShouldNotWork;
  }


  private LocalDateTime createLocalDateTime(String dateString,String pattern){
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
    return LocalDateTime.parse(dateString,formatter);
  }

  private Boolean compareDbResultWithExpected(Integer brandId, Integer productId, LocalDateTime
      localDateTime,Price expectedPrice){
    Price priceDb = priceDao.getPriceByFilter(brandId, productId, localDateTime)
        .doOnError(error -> fail("Unexpected error: " + error.getMessage()))
        .map(PriceEntity::entityToModel).block();
    return expectedPrice.equals(priceDb);
  }
}

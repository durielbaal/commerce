package com.app.api.price.domain.core;

import static org.junit.jupiter.api.Assertions.fail;

import com.app.api.price.domain.model.Price;
import com.app.api.price.domain.model.PriceFilter;
import com.app.api.price.domain.ports.inbound.GetPriceByFilterUseCase;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GetPriceByFilterUseCaseImplTest {

  @Autowired
  private GetPriceByFilterUseCase getPriceByFilterUseCase;

  @Test
  void shouldFindPrice() {
    LocalDateTime dateTimeWork = createLocalDateTime("14-06-2020 16:00:00", "dd-MM-yyyy HH:mm:ss");
    Price expectedPrice = new Price(1, 2, 35455, new BigDecimal("25.45"), "EUR");
    PriceFilter priceFilter = new PriceFilter(1, 35455, dateTimeWork);
    boolean itShouldWork = compareDbResultWithExpected(priceFilter, expectedPrice);
    assert itShouldWork;
  }

  @Test
  void shouldNotFindPrice() {
    LocalDateTime dateTimeNotWork = createLocalDateTime("14-06-2024 16:00:00", "dd-MM-yyyy HH:mm:ss");
    PriceFilter priceFilter = new PriceFilter(1, 35455, dateTimeNotWork);
    Price expectedPrice = new Price(1, 2, 35455, new BigDecimal("25.45"), "EUR");
    boolean itShouldNotWork = compareDbResultWithExpected(priceFilter, expectedPrice);
    assert !itShouldNotWork;
  }


  private LocalDateTime createLocalDateTime(String dateString,String pattern){
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
    return LocalDateTime.parse(dateString,formatter);
  }

  private Boolean compareDbResultWithExpected(PriceFilter priceFilter,Price expectedPrice){
    Price priceDb = getPriceByFilterUseCase.execute(priceFilter)
        .doOnError(error -> fail("Unexpected error: " + error.getMessage())).block();
    return expectedPrice.equals(priceDb);
  }

}

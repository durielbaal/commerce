package com.app.api.price.rest;

import static com.app.api.price.rest.adapter.PriceFilterAdapter.adapt;
import static org.junit.jupiter.api.Assertions.fail;

import com.app.api.price.domain.model.Price;
import com.app.api.price.domain.model.PriceFilter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PriceControllerTest {

  @Autowired
  private  PriceController priceController;

  @Test
  void shouldNotFindPrice() {
    LocalDateTime dateTimeNotWork = createLocalDateTime("14-06-2024 10:00:00", "dd-MM-yyyy HH:mm:ss");
    Price expectedPrice = new Price(1, 2, 35455, new BigDecimal("25.45"), "EUR");
    PriceFilter priceFilter = adapt(1, 35455, dateTimeNotWork);
    assert !compareDbResultWithExpected(priceFilter,expectedPrice);
  }

  @Test
  void shouldFindPrice() {
    LocalDateTime dateTimeWork = createLocalDateTime("14-06-2020 10:00:00", "dd-MM-yyyy HH:mm:ss");
    Price expectedPrice = new Price(1, 2, 35455, new BigDecimal("25.45"), "EUR");
    PriceFilter priceFilter = adapt(1, 35455, dateTimeWork);
    assert !compareDbResultWithExpected(priceFilter,expectedPrice);
  }

  @Test
  void shouldFindPrice1() {
    LocalDateTime dateTimeWork = createLocalDateTime("14-06-2020 16:00:00", "dd-MM-yyyy HH:mm:ss");
    Price expectedPrice = new Price(1, 2, 35455, new BigDecimal("25.45"), "EUR");
    PriceFilter priceFilter = adapt(1, 35455, dateTimeWork);
    assert compareDbResultWithExpected(priceFilter,expectedPrice);
  }

  @Test
  void shouldFindPrice2() {
    LocalDateTime dateTimeWork = createLocalDateTime("14-06-2020 21:00:00", "dd-MM-yyyy HH:mm:ss");
    Price expectedPrice = new Price(1, 1, 35455, new BigDecimal("35.50"), "EUR");
    PriceFilter priceFilter = adapt(1, 35455, dateTimeWork);
    assert compareDbResultWithExpected(priceFilter,expectedPrice);
  }

  @Test
  void shouldFindPrice3() {
    LocalDateTime dateTimeWork = createLocalDateTime("15-06-2020 10:00:00", "dd-MM-yyyy HH:mm:ss");
    Price expectedPrice = new Price(1, 3, 35455, new BigDecimal("30.50"), "EUR");
    PriceFilter priceFilter = adapt(1, 35455, dateTimeWork);
    assert compareDbResultWithExpected(priceFilter,expectedPrice);
  }

  @Test
  void shouldFindPrice4() {
    LocalDateTime dateTimeWork = createLocalDateTime("16-06-2020 21:00:00", "dd-MM-yyyy HH:mm:ss");
    Price expectedPrice = new Price(1, 4, 35455, new BigDecimal("38.95"), "EUR");
    PriceFilter priceFilter = adapt(1, 35455, dateTimeWork);
    assert compareDbResultWithExpected(priceFilter,expectedPrice);
  }


  private LocalDateTime createLocalDateTime(String dateString,String pattern){
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
    return LocalDateTime.parse(dateString,formatter);
  }

  private Boolean compareDbResultWithExpected(PriceFilter priceFilter, Price expectedPrice){
    Price priceDb = priceController.getPriceByFilter(priceFilter.getBrandId(),priceFilter.getProductId(),priceFilter.getCertainDate())
        .block();
    if(priceDb == null) return false;
    return expectedPrice.equals(priceDb);
  }

}

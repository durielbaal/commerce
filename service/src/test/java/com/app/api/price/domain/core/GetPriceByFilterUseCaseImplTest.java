package com.app.api.price.domain.core;

import com.app.api.price.domain.model.Price;
import com.app.api.price.domain.model.PriceFilter;
import com.app.api.price.domain.ports.inbound.GetPriceByFilterUseCase;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;

@SpringBootTest
@TestConstructor(autowireMode = AutowireMode.ALL)
public class GetPriceByFilterUseCaseImplTest {

  private final GetPriceByFilterUseCase getPriceByFilterUseCase;

  public GetPriceByFilterUseCaseImplTest(GetPriceByFilterUseCase getPriceByFilterUseCase) {
    this.getPriceByFilterUseCase = getPriceByFilterUseCase;
  }

  @Test
  void shouldReturnPriceWhenFilterMatches() {
    LocalDateTime targetDate = parseDateTime("14-06-2020 16:00:00", "dd-MM-yyyy HH:mm:ss");
    LocalDateTime startDate = parseDateTime("14-06-2020 15:00:00", "dd-MM-yyyy HH:mm:ss");
    LocalDateTime endDate = parseDateTime("14-06-2020 18:30:00", "dd-MM-yyyy HH:mm:ss");
    Price expectedPrice = new Price(1, 2, 35455, new BigDecimal("25.45"),startDate,endDate);
    PriceFilter priceFilter = new PriceFilter(1, 35455, targetDate);

    boolean resultMatches = compareDatabaseResultWithExpected(priceFilter, expectedPrice);
    Assertions.assertTrue(resultMatches, "The expected price should match the database result.");
  }

  @Test
  void shouldReturnEmptyWhenFilterDoesNotMatch() {
    LocalDateTime targetDate = parseDateTime("14-06-2024 16:00:00", "dd-MM-yyyy HH:mm:ss");
    LocalDateTime startDate = parseDateTime("14-06-2020 15:00:00", "dd-MM-yyyy HH:mm:ss");
    LocalDateTime endDate = parseDateTime("14-06-2020 18:30:00", "dd-MM-yyyy HH:mm:ss");
    PriceFilter priceFilter = new PriceFilter(1, 35455, targetDate);
    Price expectedPrice = new Price(1, 2, 35455, new BigDecimal("25.45"),startDate,endDate);

    boolean resultDoesNotMatch = compareDatabaseResultWithExpected(priceFilter, expectedPrice);
    Assertions.assertFalse(resultDoesNotMatch, "The database should not return a price for this filter.");
  }

  private LocalDateTime parseDateTime(String dateString, String pattern) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
    return LocalDateTime.parse(dateString, formatter);
  }

  private boolean compareDatabaseResultWithExpected(PriceFilter priceFilter, Price expectedPrice) {
    Price priceFromDatabase = getPriceByFilterUseCase.execute(priceFilter)
        .doOnError(error -> Assertions.fail("Unexpected error: " + error.getMessage()))
        .block();
    return expectedPrice.equals(priceFromDatabase);
  }
}

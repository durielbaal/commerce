package com.app.api.price.rest;

import static com.app.api.price.rest.adapter.PriceFilterAdapter.adapt;
import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import com.app.api.price.domain.model.Price;
import com.app.api.price.domain.model.PriceFilter;
import com.app.api.price.domain.model.PriceRequest;
import com.app.api.price.domain.ports.inbound.GetPriceByFilterUseCase;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;
import reactor.core.publisher.Mono;

@SpringBootTest
@TestConstructor(autowireMode = AutowireMode.ALL)
public class PriceControllerTest {

  private final PriceController priceController;
  @Mock
  private GetPriceByFilterUseCase getPriceByFilterUseCase;

  @InjectMocks
  private PriceController priceControllerMock;

  private LocalDateTime certainDate;
  public PriceControllerTest(PriceController priceController) {
    this.priceController = priceController;
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    certainDate = LocalDateTime.of(2025, 2, 13, 14, 30, 0);
  }

  @Test
  void testGetPriceByFilter_Success() {
    Integer brandId = 1;
    Integer productId = 2;
    LocalDateTime startDate= Mockito.mock(LocalDateTime.class);
    LocalDateTime endDate= Mockito.mock(LocalDateTime.class);
    Price price = new Price(1, 1,1,new BigDecimal(100.0),startDate, endDate);
    when(getPriceByFilterUseCase.execute(any())).thenReturn(Mono.just(price));
    PriceRequest priceRequest= new PriceRequest(brandId, productId, certainDate);
    Mono<Price> result = priceController.getPriceByFilter(priceRequest);
    result.doOnTerminate(() -> {
      verify(getPriceByFilterUseCase, times(1)).execute(any());
    }).subscribe(priceResult -> {
      assertNotNull(priceResult);
      assertEquals(1, priceResult.getBrandId());
      assertEquals(1, priceResult.getProductId());
      assertEquals(new BigDecimal("100.0"), priceResult.getPrice());
    });
  }

  @Test
  void testGetPriceByFilter_NotFound() {
    Integer brandId = 1;
    Integer productId = 2;
    when(getPriceByFilterUseCase.execute(any())).thenReturn(Mono.empty());
    PriceRequest priceRequest= new PriceRequest(brandId, productId, certainDate);
    Mono<Price> result = priceController.getPriceByFilter(priceRequest);
    result.doOnTerminate(() -> {
      verify(getPriceByFilterUseCase, times(1)).execute(any());
    }).subscribe(Assertions::assertNull);
  }

  @Test
  void testGetPriceByFilter_Error() {
    Integer brandId = 1;
    Integer productId = 2;
    when(getPriceByFilterUseCase.execute(any())).thenReturn(Mono.error(new RuntimeException("Error fetching price")));
    PriceRequest priceRequest= new PriceRequest(brandId, productId, certainDate);
    Mono<Price> result = priceController.getPriceByFilter(priceRequest);
    result.doOnTerminate(() -> {
      verify(getPriceByFilterUseCase, times(1)).execute(any());
    }).subscribe(priceResult -> {
      fail("Expected error, but got a result");
    }, error -> {
      assertInstanceOf(RuntimeException.class, error);
      assertEquals("Error fetching price", error.getMessage());
    });
  }

  @Test
  void shouldNotFindPriceByFilter() {
    LocalDateTime dateTimeNotWork = parseDateTime("14-06-2024 10:00:00", "dd-MM-yyyy HH:mm:ss");
    LocalDateTime startDate = parseDateTime("14-06-2020 15:00:00", "dd-MM-yyyy HH:mm:ss");
    LocalDateTime endDate = parseDateTime("14-06-2020 18:30:00", "dd-MM-yyyy HH:mm:ss");
    Price expectedPrice = new Price(1, 2, 35455, new BigDecimal("25.45"), startDate, endDate);
    PriceFilter priceFilter = adapt(1, 35455, dateTimeNotWork);
    assert !compareDbResultWithExpected(priceFilter,expectedPrice);
  }

  @Test
  void shouldFindPriceByFilter1() {
    LocalDateTime dateTimeWork = parseDateTime("14-06-2020 10:00:00", "dd-MM-yyyy HH:mm:ss");
    LocalDateTime startDate = parseDateTime("14-06-2020 00:00:00", "dd-MM-yyyy HH:mm:ss");
    LocalDateTime endDate = parseDateTime("31-12-2020 23:59:59", "dd-MM-yyyy HH:mm:ss");
    Price expectedPrice = new Price(1, 1, 35455, new BigDecimal("35.50"), startDate, endDate);
    PriceFilter priceFilter = adapt(1, 35455, dateTimeWork);
    assert compareDbResultWithExpected(priceFilter,expectedPrice);
  }

  @Test
  void shouldFindPriceByFilter2() {
    LocalDateTime dateTimeWork = parseDateTime("14-06-2020 16:00:00", "dd-MM-yyyy HH:mm:ss");
    LocalDateTime startDate = parseDateTime("14-06-2020 15:00:00", "dd-MM-yyyy HH:mm:ss");
    LocalDateTime endDate = parseDateTime("14-06-2020 18:30:00", "dd-MM-yyyy HH:mm:ss");
    Price expectedPrice = new Price(1, 2, 35455, new BigDecimal("25.45"), startDate, endDate);
    PriceFilter priceFilter = adapt(1, 35455, dateTimeWork);
    assert compareDbResultWithExpected(priceFilter,expectedPrice);
  }

  @Test
  void shouldFindPriceByFilter3() {
    LocalDateTime dateTimeWork = parseDateTime("14-06-2020 21:00:00", "dd-MM-yyyy HH:mm:ss");
    LocalDateTime startDate = parseDateTime("14-06-2020 00:00:00", "dd-MM-yyyy HH:mm:ss");
    LocalDateTime endDate = parseDateTime("31-12-2020 23:59:59", "dd-MM-yyyy HH:mm:ss");
    Price expectedPrice = new Price(1, 1, 35455, new BigDecimal("35.50"), startDate, endDate);
    PriceFilter priceFilter = adapt(1, 35455, dateTimeWork);
    assert compareDbResultWithExpected(priceFilter,expectedPrice);
  }

  @Test
  void shouldFindPriceByFilter4() {
    LocalDateTime dateTimeWork = parseDateTime("15-06-2020 10:00:00", "dd-MM-yyyy HH:mm:ss");
    LocalDateTime startDate = parseDateTime("15-06-2020 00:00:00", "dd-MM-yyyy HH:mm:ss");
    LocalDateTime endDate = parseDateTime("15-06-2020 11:00:00", "dd-MM-yyyy HH:mm:ss");
    Price expectedPrice = new Price(1, 3, 35455, new BigDecimal("30.50"), startDate, endDate);
    PriceFilter priceFilter = adapt(1, 35455, dateTimeWork);
    assert compareDbResultWithExpected(priceFilter,expectedPrice);
  }
  @Test
  void shouldFindPriceByFilter5() {
    LocalDateTime dateTimeWork = parseDateTime("16-06-2020 21:00:00", "dd-MM-yyyy HH:mm:ss");
    LocalDateTime startDate = parseDateTime("15-06-2020 16:00:00", "dd-MM-yyyy HH:mm:ss");
    LocalDateTime endDate = parseDateTime("31-12-2020 23:59:59", "dd-MM-yyyy HH:mm:ss");
    Price expectedPrice = new Price(1, 4, 35455, new BigDecimal("38.95"), startDate, endDate);
    PriceFilter priceFilter = adapt(1, 35455, dateTimeWork);
    assert compareDbResultWithExpected(priceFilter,expectedPrice);
  }

  private LocalDateTime parseDateTime(String dateString, String pattern) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
    return LocalDateTime.parse(dateString, formatter);
  }

  private Boolean compareDbResultWithExpected(PriceFilter priceFilter, Price expectedPrice){
    PriceRequest priceRequest= new PriceRequest(priceFilter.getBrandId(), priceFilter.getProductId(), priceFilter.getCertainDate());
    Price priceDb = priceController.getPriceByFilter(priceRequest)
        .block();
    if(priceDb == null) return false;
    return expectedPrice.equals(priceDb);
  }

}

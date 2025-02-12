package com.app.api.shared.domain.ports.outbound;

import static com.app.api.price.domain.ports.outbound.PricePersistencePort.GET_PRICE_BY_FILTER_ADDRESS;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;

import com.app.api.price.domain.model.PriceFilter;
import com.app.shared.domain.model.Message;
import com.app.shared.domain.ports.outbound.OutboundPort;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;

@SpringBootTest
public class OutboundPortTest {

  @Test
  void initialRegisterCompleted() {
    PriceFilter priceFilter = mock(PriceFilter.class);
    requestEventTest(GET_PRICE_BY_FILTER_ADDRESS);
    assert true;
  }

  @Test
  void normalRegisterCompleted() {
    OutboundPort.register("Test1", (body) -> Mono.empty());
    requestEventTest("Test1");
    assert true;
  }

  public void requestEventTest(String tag){
    PriceFilter priceFilter = mock(PriceFilter.class);
    try{
      OutboundPort.requestEvent(new Message<>(tag, priceFilter));
    } catch (IllegalArgumentException e){
      fail("IllegalArgumentException: ".concat(e.getMessage()));
    } catch(Exception e){
      fail("Generic exception: ".concat(e.getMessage()));
    }
  }
}

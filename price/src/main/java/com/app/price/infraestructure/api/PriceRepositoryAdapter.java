package com.app.price.infraestructure.api;

import com.app.price.core.domain.model.Price;
import com.app.price.core.domain.port.input.PriceInputPort;
import com.app.price.core.domain.port.output.PriceOutputPort;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class PriceRepositoryAdapter implements PriceInputPort {


  private final PriceOutputPort priceRepositoryPort;


  private final DatabaseClient databaseClient;
  /**
   * Finds the applicable price for a given product, brand, and application date.
   *
   * @param productId  The identifier of the product.
   * @param brandId    The identifier of the brand.
   * @param certainDate    Date of searching.
   * @return A {@link Mono} containing the applicable {@link Price}, or empty if no price is found.
   */
  @Override
  public Mono<Price> findByBrandAndProductAndDate(Integer brandId, Integer productId, LocalDateTime certainDate){
    String sql = "SELECT * " +
        "FROM Prices " +
        "WHERE brand_id = :brandId " +
        "AND product_id = :productId " +
        "AND :currentDate BETWEEN start_date AND end_date " +
        "ORDER BY priority DESC " +
        "LIMIT 1";
    return databaseClient.sql(sql)
        .bind("brandId", brandId)
        .bind("productId", productId)
        .bind("currentDate", certainDate)
        .map(row -> {
          Price price = new Price();
          price.setId(row.get("id", Long.class));
          price.setBrandId(row.get("brand_id", Integer.class));
          price.setProductId(row.get("product_id", Integer.class));
          price.setStartDate(row.get("start_date", LocalDateTime.class));
          price.setEndDate(row.get("end_date", LocalDateTime.class));
          price.setPriceList(row.get("price_list", Integer.class));
          price.setPriority(row.get("priority", Integer.class));
          price.setPrice(row.get("price", BigDecimal.class));
          price.setCurrency(row.get("curr", String.class));
          return price;
        })
        .one();
  }

  /**
   * Finds a price by its ID.
   *
   * @param id The price ID.
   * @return A Mono containing the price, or empty if not found.
   */
  @Override
  public Mono<Price> findById(Long id) {
    return priceRepositoryPort.findById(id);
  }

  /**
   * Retrieves all prices.
   *
   * @return A Flux containing all prices.
   */
  @Override
  public Flux<Price> findAll() {
    return priceRepositoryPort.findAll();
  }

  /**
   * Saves a price.
   *
   * @param price The price entity.
   * @return A Mono containing the saved price.
   */
  @Override
  public Mono<Price> save(Price price) {
    return priceRepositoryPort.save(price);
  }
}

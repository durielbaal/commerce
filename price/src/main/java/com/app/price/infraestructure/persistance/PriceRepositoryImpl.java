package com.app.price.infraestructure.persistance;

import com.app.price.core.domain.model.Price;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Mono;
@RequiredArgsConstructor
public class PriceRepositoryImpl implements  CustomPriceRepository{

  private final DatabaseClient databaseClient;
  @Override
  public Mono<Price> findPriceByBrandAndProductAndDate(Integer brandId, Integer productId, LocalDateTime certainDate) {
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
}

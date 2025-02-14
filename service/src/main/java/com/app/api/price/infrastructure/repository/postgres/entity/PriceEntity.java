package com.app.api.price.infrastructure.repository.postgres.entity;

import com.app.api.price.domain.model.Price;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("Price")
public class PriceEntity {

  @Id
  private Long id;
  private Integer brandId;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private Integer priceList;
  private Integer productId;
  private Integer priority;
  private BigDecimal price;
  @Column("curr")
  private String currency;

  public Price entityToModel(){
    return new Price(brandId, priceList, productId, price,startDate,endDate);
  }

}

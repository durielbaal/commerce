package com.app.api.price.domain.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;


public class PriceFilter {
  private Integer brandId;
  private Integer productId;
  private LocalDateTime certainDate;

  public PriceFilter(Integer brandId, Integer productId, LocalDateTime certainDate) {
    this.brandId = brandId;
    this.productId = productId;
    this.certainDate = certainDate;
  }

  public PriceFilter(){
  }

  public LocalDateTime getCertainDate() {
    return certainDate;
  }

  public Integer getProductId() {
    return productId;
  }

  public Integer getBrandId() {
    return brandId;
  }

}

package com.app.api.price.domain.model;

import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

public class PriceRequest {
  private Integer brandId;
  private Integer productId;

  @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
  private LocalDateTime certainDate;

  public PriceRequest(Integer brandId, Integer productId, LocalDateTime certainDate) {
    this.brandId = brandId;
    this.productId = productId;
    this.certainDate = certainDate;
  }

  public Integer getBrandId() {
    return brandId;
  }

  public void setBrandId(Integer brandId) {
    this.brandId = brandId;
  }

  public Integer getProductId() {
    return productId;
  }

  public void setProductId(Integer productId) {
    this.productId = productId;
  }

  public LocalDateTime getCertainDate() {
    return certainDate;
  }

  public void setCertainDate(LocalDateTime certainDate) {
    this.certainDate = certainDate;
  }
}


package com.app.api.price.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Price {
  private Integer brandId;
  private Integer priceList;
  private Integer productId;
  private BigDecimal price;
  private LocalDateTime startDate;
  private LocalDateTime endDate;

  public Price(Integer brandId, Integer priceList, Integer productId, BigDecimal price,LocalDateTime startDate,
      LocalDateTime endDate) {
    this.brandId = brandId;
    this.priceList = priceList;
    this.productId = productId;
    this.price = price;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Price price1 = (Price) o;
    return Objects.equals(brandId, price1.brandId) && Objects.equals(priceList, price1.priceList) && Objects.equals(
        productId, price1.productId) && Objects.equals(price, price1.price) && Objects.equals(startDate, price1.startDate)
        && Objects.equals(endDate, price1.endDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(brandId, priceList, productId, price, startDate, endDate);
  }

  public Integer getBrandId() {
    return brandId;
  }

  public Integer getPriceList() {
    return priceList;
  }

  public Integer getProductId() {
    return productId;
  }

  public BigDecimal getPrice() {
    return price;
  }
  
  public LocalDateTime getStartDate() {
    return startDate;
  }

  public LocalDateTime getEndDate() {
    return endDate;
  }
}

package com.app.api.price.domain.model;

import java.math.BigDecimal;

public class Price {
  private Integer brandId;
  private Integer priceList;
  private Integer productId;
  private BigDecimal price;
  private String currency;

  public Price(Integer brandId, Integer priceList, Integer productId, BigDecimal price, String currency) {
    this.brandId = brandId;
    this.priceList = priceList;
    this.productId = productId;
    this.price = price;
    this.currency = currency;
  }

  public Price(){}

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

  public String getCurrency() {
    return currency;
  }
}

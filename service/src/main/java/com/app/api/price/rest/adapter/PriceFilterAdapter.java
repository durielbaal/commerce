package com.app.api.price.rest.adapter;

import com.app.api.price.domain.model.PriceFilter;
import java.time.LocalDateTime;

public class PriceFilterAdapter {

  public static PriceFilter adapt(Integer brandId, Integer productId, LocalDateTime certainDate){
    return new PriceFilter(brandId,productId,certainDate);
  }
}

package com.app.api.price.domain.model;

import java.time.LocalDateTime;

public record PriceFilter(Integer brandId, Integer productId, LocalDateTime certainDate) {

}

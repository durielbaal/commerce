package com.app.price.core.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Information of rates with certain products of brands in a range of dates.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("prices")
public class Price {
  @Id
  private Long id;
  private Integer brandId;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private Integer priceList;
  private Integer productId;
  private Integer priority;
  private BigDecimal price;
  private String currency;

}

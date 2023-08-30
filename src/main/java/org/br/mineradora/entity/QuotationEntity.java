package org.br.mineradora.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "quotation")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuotationEntity {

  @Id
  @GeneratedValue
  private Long id;
  private LocalDateTime date;
  @Column(name = "currency_price")
  private BigDecimal currencyPrice;
  @Column(name = "pct_change")
  private String pctChange;
  private String pair;

}

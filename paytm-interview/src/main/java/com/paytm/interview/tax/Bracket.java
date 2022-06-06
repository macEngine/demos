package com.paytm.interview.tax;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Bracket {
  private BigDecimal maxIncome;
  private BigDecimal taxRate;
  // Ignore Constructor, Setter and Getter.
}

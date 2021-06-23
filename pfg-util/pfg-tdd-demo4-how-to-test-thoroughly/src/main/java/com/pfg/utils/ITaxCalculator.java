package com.pfg.utils;

import java.math.BigDecimal;

public interface ITaxCalculator {
  BigDecimal calculateTaxFee(BigDecimal income);

  BigDecimal calculateInitialIncome(BigDecimal rate);
}

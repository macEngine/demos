package com.paytm.interview.tax;

import java.math.BigDecimal;

public interface ITaxCalculator {
  BigDecimal calculateTaxFee(BigDecimal income);

  BigDecimal calculateInitialIncome(BigDecimal rate);
}

package com.pfg.utils;
import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class TaxCalculator implements ITaxCalculator {

  private final static String TAX_BRACKETS_INPUT = "tax_brackets_input.txt";
  public final static double MAX_INCOME = 10E20d;
  private static List<Bracket> brackets;

  static {
    brackets = TaxUtils.loadFileBrackets(new File(
        TaxCalculator.class.getClassLoader().getResource(TAX_BRACKETS_INPUT).getFile()));
  }

  public BigDecimal calculateTaxFee(BigDecimal income) {
    return TaxUtils.roundDownToWholeNumber(calculateTaxFeeWithoutRoundDown(income));
  }

  public BigDecimal calculateTaxFeeWithoutRoundDown(BigDecimal income) {
    if (Objects.isNull(income)) {
      throw new IllegalArgumentException("Income can not be null.");
    }
    if (TaxUtils.compareTo(income, BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("Income can not be negative, income=" + income);
    }

    BigDecimal taxesToPay = BigDecimal.ZERO;

    for (int i = 0; i < brackets.size(); i++) {
      Bracket currentBracket = brackets.get(i);
      if (currentBracket.getTaxRate().compareTo(BigDecimal.ZERO) == 0) {
        taxesToPay = taxesToPay.add(BigDecimal.ZERO);
      } else {
        Bracket prevBracket = brackets.get(i - 1);
        if (income.compareTo(currentBracket.getMaxIncome()) < 0) {
          taxesToPay = taxesToPay.add(
              (income.subtract(prevBracket.getMaxIncome())).multiply(currentBracket.getTaxRate()));
          // Possibly, income is less than first bracket's maxIncome, in such case, taxes is zero.
          if (taxesToPay.compareTo(BigDecimal.ZERO) < 0) {
            taxesToPay = BigDecimal.ZERO;
          }
          break;
        } else {
          taxesToPay = taxesToPay.add(
              (currentBracket.getMaxIncome().subtract(prevBracket.getMaxIncome())).multiply(currentBracket
                  .getTaxRate()));
        }
      }
    }
    return taxesToPay;
  }

  public BigDecimal calculateInitialIncome(BigDecimal rate) {
    if (Objects.isNull(rate)) {
      throw new IllegalArgumentException("Rate can not be null.");
    }
    if (TaxUtils.compareTo(rate, BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("Rate can not be negative, rate=" + rate);
    }

    if (rate.compareTo(brackets.get(brackets.size() - 1).getTaxRate()) >= 0) {
      System.out.println("No such value exist.");
      return BigDecimal.valueOf(-1);
    }
    BigDecimal low = BigDecimal.ZERO;
    BigDecimal high = BigDecimal.valueOf(MAX_INCOME);
    BigDecimal mid = TaxUtils.divideRoundDown(low.add(high), BigDecimal.valueOf(2));
    BigDecimal currentRate = calculateTaxFeeWithoutRoundDown(mid).divide(mid);
    while (Math.abs(TaxUtils.compareTo(currentRate, rate, TaxUtils.DEFAULT_DIVISION_SCALE)) > 0
        && Math.abs(TaxUtils.compareTo(low, high, TaxUtils.DEFAULT_DIVISION_SCALE)) > 0) {
      if (currentRate.compareTo(rate) < 0) {
        low = mid;
      } else {
        high = mid;
      }
      mid = TaxUtils.divideRoundDown(low.add(high), BigDecimal.valueOf(2));
      currentRate = TaxUtils.divideRoundDown(calculateTaxFeeWithoutRoundDown(mid), mid);
    }
    return mid;
  }
}

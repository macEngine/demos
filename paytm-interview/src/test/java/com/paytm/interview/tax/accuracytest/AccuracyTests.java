package com.paytm.interview.tax.accuracytest;

import com.paytm.interview.tax.ITaxCalculator;
import com.paytm.interview.tax.TaxCalculator;
import com.paytm.interview.tax.TaxUtils;
import org.junit.Test;

import java.math.BigDecimal;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class AccuracyTests {
  private ITaxCalculator taxCalculator = new TaxCalculator();

  @Test
  public void testCalculateTaxFee_givenNormalValue_thenOk() {
    System.out.println("tax(1)=" + TaxUtils.format(taxCalculator.calculateTaxFee(BigDecimal.valueOf(1)))); // 0
    assertEquals(taxCalculator.calculateTaxFee(BigDecimal.valueOf(1)), BigDecimal.valueOf(0));

    System.out.println("tax(10000)=" + TaxUtils.format(taxCalculator.calculateTaxFee(BigDecimal.valueOf(10000)))); // 0
    assertEquals(taxCalculator.calculateTaxFee(BigDecimal.valueOf(10000)), BigDecimal.valueOf(0));

    System.out.println("tax(10009)=" + TaxUtils.format(taxCalculator.calculateTaxFee(BigDecimal.valueOf(10009)))); // 0
    assertEquals(taxCalculator.calculateTaxFee(BigDecimal.valueOf(10009)), BigDecimal.valueOf(0));

    System.out.println("tax(10010)=" + TaxUtils.format(taxCalculator.calculateTaxFee(BigDecimal.valueOf(10010)))); // 1
    assertEquals(taxCalculator.calculateTaxFee(BigDecimal.valueOf(10010)), BigDecimal.valueOf(1));

    System.out.println("tax(12000)=" + TaxUtils.format(taxCalculator.calculateTaxFee(BigDecimal.valueOf(12000)))); // 200
    assertEquals(taxCalculator.calculateTaxFee(BigDecimal.valueOf(12000)), BigDecimal.valueOf(200));

    System.out.println("tax(56789)=" + TaxUtils.format(taxCalculator.calculateTaxFee(BigDecimal.valueOf(56789)))); // 8697
    assertEquals(taxCalculator.calculateTaxFee(BigDecimal.valueOf(56789)), BigDecimal.valueOf(8697));

    System.out.println("tax(1234567)=" + TaxUtils.format(taxCalculator.calculateTaxFee(BigDecimal.valueOf(1234567)))); // 473326
    assertEquals(taxCalculator.calculateTaxFee(BigDecimal.valueOf(1234567)), BigDecimal.valueOf(473326));
  }

  @Test
  public void testCalculateTaxFee_givenCornerCase_thenOk() {
    // test zero income
    System.out.println("tax(0)=" + TaxUtils.format(taxCalculator.calculateTaxFee(BigDecimal.valueOf(0)))); // 0
    assertEquals(taxCalculator.calculateTaxFee(BigDecimal.valueOf(0)), BigDecimal.valueOf(0));

    // test large income (larger than Long's max value)
    BigDecimal income = BigDecimal.valueOf(12345678901234567890123d);
    BigDecimal expect = new BigDecimal("399999999999999979500");
    System.out.println("tax(0)=" + TaxUtils.format(taxCalculator.calculateTaxFee(income)));
    assertTrue(TaxUtils.compareTo(taxCalculator.calculateTaxFee(income), expect) == 0);
  }

  @Test
  public void testInitialIncomeBinarySearch_givenNormalRate_thenOk() {
    System.out.println("overall(0.00)=" + TaxUtils.format(taxCalculator.calculateInitialIncome(BigDecimal.valueOf(0)))); // < 10000
    assertTrue(TaxUtils.compareTo(taxCalculator.calculateInitialIncome(BigDecimal.valueOf(0)), BigDecimal.valueOf(10000)) < 0);

    System.out.println("overall(0.06)=" + TaxUtils.format(taxCalculator.calculateInitialIncome(BigDecimal.valueOf(0.06)))); // 25000
    assertTrue(TaxUtils.compareTo(taxCalculator.calculateInitialIncome(BigDecimal.valueOf(0.06)), BigDecimal.valueOf(25000)) == 0);

    System.out.println("overall(0.09)=" + TaxUtils.format(taxCalculator.calculateInitialIncome(BigDecimal.valueOf(0.09)))); // 34375
    assertTrue(TaxUtils.compareTo(taxCalculator.calculateInitialIncome(BigDecimal.valueOf(0.09)), BigDecimal.valueOf(34375)) == 0);

    System.out.println("overall(0.32)=" + TaxUtils.format(taxCalculator.calculateInitialIncome(BigDecimal.valueOf(0.32)))); // 256250
    System.out.println(taxCalculator.calculateInitialIncome(BigDecimal.valueOf(0.32)));
    assertTrue(TaxUtils.compareTo(taxCalculator.calculateInitialIncome(BigDecimal.valueOf(0.32)), BigDecimal.valueOf(256250)) == 0);


    System.out.println("overall(0.40)=" + TaxUtils.format(taxCalculator.calculateInitialIncome(BigDecimal.valueOf(0.40)))); // -1
    assertTrue(TaxUtils.compareTo(taxCalculator.calculateInitialIncome(BigDecimal.valueOf(0.40)), BigDecimal.valueOf(-1)) == 0);

  }

  @Test
  public void testInitialIncomeBinarySearch_givenCornerCaseRate_thenOk() {
    System.out.println("overall(0.41)=" + TaxUtils.format(taxCalculator.calculateInitialIncome(BigDecimal.valueOf(0.41)))); // -1
    assertTrue(TaxUtils.compareTo(taxCalculator.calculateInitialIncome(BigDecimal.valueOf(0)), BigDecimal.valueOf(10000)) < 0);

    System.out.println("overall(1.00)=" + TaxUtils.format(taxCalculator.calculateInitialIncome(BigDecimal.valueOf(1.00)))); // -1
    assertTrue(TaxUtils.compareTo(taxCalculator.calculateInitialIncome(BigDecimal.valueOf(0)), BigDecimal.valueOf(10000)) < 0);
  }
}

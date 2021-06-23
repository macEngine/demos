package com.pfg.utils.failuretests;

import com.pfg.utils.ITaxCalculator;
import com.pfg.utils.TaxCalculator;
import com.pfg.utils.TaxUtils;
import org.junit.Test;

import java.io.File;
import java.math.BigDecimal;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;

public class FailureTests {
  private ITaxCalculator taxCalculator = new TaxCalculator();

  @Test
  public void testCalculateTaxFee_givenNegativeIncome_thenException() {
    try {
      taxCalculator.calculateTaxFee(BigDecimal.valueOf(-1));
      fail("No exception thrown.");
    } catch (Exception ex) {
      assertTrue(ex instanceof IllegalArgumentException);
      assertTrue(ex.getMessage().contains("Income can not be negative"));
    }
  }

  @Test
  public void testCalculateTaxFee_givenNullIncome_thenException() {
    try {
      taxCalculator.calculateTaxFee(null);
      fail("No exception thrown.");
    } catch (Exception ex) {
      assertTrue(ex instanceof IllegalArgumentException);
      assertTrue(ex.getMessage().contains("Income can not be null"));
    }
  }
  @Test
  public void testCalculateInitialIncome_givenNegativeRate_thenException() {
    try {
      taxCalculator.calculateInitialIncome(BigDecimal.valueOf(-1));
      fail("No exception thrown.");
    } catch (Exception ex) {
      assertTrue(ex instanceof IllegalArgumentException);
      assertTrue(ex.getMessage().contains("Rate can not be negative"));
    }
  }

  @Test
  public void testCalculateInitialIncome_givenNullRate_thenException() {
    try {
      taxCalculator.calculateInitialIncome(null);
      fail("No exception thrown.");
    } catch (Exception ex) {
      assertTrue(ex instanceof IllegalArgumentException);
      assertTrue(ex.getMessage().contains("Rate can not be null"));
    }
  }

  @Test
  public void testLoadFileBrackets_givenNullFile_thenException() {
    try {
      TaxUtils.loadFileBrackets(null);
      fail("No exception thrown.");
    } catch (Exception ex) {
      assertTrue(ex instanceof IllegalArgumentException);
      assertTrue(ex.getMessage().contains("Tax Bracket File can not be null"));
    }
  }

  @Test
  public void testLoadFileBrackets_givenNotExistFile_thenException() {
    try {
      TaxUtils.loadFileBrackets(new File("Not exist"));
      fail("No exception thrown.");
    } catch (Exception ex) {
      assertTrue(ex instanceof IllegalArgumentException);
      assertTrue(ex.getMessage().contains("Tax Bracket File does not exist"));
    }
  }

  @Test
  public void testLoadFileBrackets_givenInvalidFormatFile_thenException() {
    try {
      TaxUtils.loadFileBrackets(new File(
          FailureTests.class.getClassLoader().getResource("tax_brackets_input_invalid.txt").getFile()));
      fail("No exception thrown.");
    } catch (Exception ex) {
      ex.printStackTrace();
      assertTrue(ex instanceof IllegalArgumentException);
      assertTrue(ex.getMessage().contains("Tax Bracket File should have income cap and marginal tax rate"));
    }
  }
}

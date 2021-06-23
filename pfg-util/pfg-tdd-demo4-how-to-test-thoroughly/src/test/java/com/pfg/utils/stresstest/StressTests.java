package com.pfg.utils.stresstest;


import com.pfg.utils.ITaxCalculator;
import com.pfg.utils.TaxCalculator;
import com.pfg.utils.TaxUtils;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static junit.framework.TestCase.assertTrue;

public class StressTests {
  private ITaxCalculator taxCalculator = new TaxCalculator();

  /** The test count. */
  private static final int testCount = 10000;

  /** time started to test */
  private LocalTime start;

  /** The last error occurred */
  private Throwable lastError;

  /**
   * Initialize variables.
   */
  @Before
  public void setUp() {
    start = LocalTime.now();
    lastError = null;
  }

  @Test
  public void stressTestCalculateTaxFee() throws Throwable {
    ExecutorService executorService = Executors.newFixedThreadPool(100);
    List<Future> futures = new ArrayList();

    for (int i = 0; i < testCount; i++) {
      futures.add(executorService.submit(() -> taxCalculator.calculateTaxFee(BigDecimal.valueOf(1234567))));
    }

    for (Future future : futures) {
      try {
        BigDecimal result = (BigDecimal) future.get();
        assertTrue("Tax Fee should be equals.", TaxUtils.compareTo(result, BigDecimal.valueOf(473326)) == 0);
      } catch (Throwable e) {
        lastError = e;
      }
    }

    if (lastError != null) {
      throw lastError;
    }

    System.out.println("Run test for " + testCount + " times takes "
        + (Duration.between(start, LocalTime.now()).toMillis()) + "ms");

  }

  @Test
  public void stressTestCalculateInitialIncome() throws Throwable {
    ExecutorService executorService = Executors.newFixedThreadPool(100);
    List<Future> futures = new ArrayList();

    for (int i = 0; i < testCount; i++) {
      futures.add(executorService.submit(() -> taxCalculator.calculateInitialIncome(BigDecimal.valueOf(0.32))));
    }

    for (Future future : futures) {
      try {
        BigDecimal result = (BigDecimal) future.get();
        assertTrue("Initial Income should be equals.", TaxUtils.compareTo(result, BigDecimal.valueOf(256250)) == 0);
      } catch (Throwable e) {
        lastError = e;
      }
    }

    if (lastError != null) {
      throw lastError;
    }

    System.out.println("Run test for " + testCount + " times takes "
        + (Duration.between(start, LocalTime.now()).toMillis()) + "ms");
  }
}

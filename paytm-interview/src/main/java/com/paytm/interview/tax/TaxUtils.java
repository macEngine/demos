package com.paytm.interview.tax;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TaxUtils {
  public static final int DEFAULT_DIVISION_SCALE = 10;
  public static final int DEFAULT_COMPARE_RESULT_SCALE = 2;
  public static final RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.DOWN;

  /**
   * Read taxRate brackets from file.
   * Suppose the data format as below:
   *    10000            0.00
   *    30000            0.10
   *    100000           0.25
   *    100000000000     0.40
   */
  public static List<Bracket> loadFileBrackets(File taxBracketsFile) {
    if (Objects.isNull(taxBracketsFile)) {
      throw new IllegalArgumentException("Tax Bracket File can not be null.");
    }
    if (!taxBracketsFile.exists()) {
      throw new IllegalArgumentException("Tax Bracket File does not exist, fileName=" + taxBracketsFile.getName());
    }

    try {
      FileReader file = new FileReader(taxBracketsFile);
      BufferedReader in = new BufferedReader(new BufferedReader(file));
      ArrayList<Bracket> result = new ArrayList<>();

      String line;
      while ((line = in.readLine()) != null) {
        String splitArray[] = line.split("\\s+");
        if (splitArray.length < 2) {
          throw new IllegalArgumentException("Tax Bracket File should have income cap and marginal tax rate.");
        }
        if ("--".equals(splitArray[0].trim())) {
          splitArray[0] = String.valueOf(TaxCalculator.MAX_INCOME);
        }
        Bracket bracket = new Bracket(new BigDecimal(splitArray[0].trim()), new BigDecimal(splitArray[1].trim()));
        result.add(bracket);
      }
      return result;
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("Could not read Tax Bracket File.");
      return null;
    }
  }

  /**
   * Round down to a whole number.
   */
  public static BigDecimal roundDownToWholeNumber(BigDecimal value) {
    return value.setScale(0, DEFAULT_ROUNDING_MODE);
  }

  /**
   * Round down to a whole number, and return String.
   */
  public static String format(BigDecimal value) {
    return String.valueOf(roundDownToWholeNumber(value));
  }

  public static BigDecimal divideRoundDown(BigDecimal value1, BigDecimal value2) {
    return value1.divide(value2, DEFAULT_DIVISION_SCALE, DEFAULT_ROUNDING_MODE);
  }

  public static int compareTo(BigDecimal value1, BigDecimal value2, int scale) {
    return value1.setScale(scale, DEFAULT_ROUNDING_MODE).compareTo(value2.setScale(scale, DEFAULT_ROUNDING_MODE));
  }

  public static int compareTo(BigDecimal value1, BigDecimal value2) {
    return compareTo(value1, value2, DEFAULT_COMPARE_RESULT_SCALE);
  }
}

package com.paytm.interview.tax;

import com.paytm.interview.tax.accuracytest.AccuracyTests;
import com.paytm.interview.tax.failuretest.FailureTests;
import com.paytm.interview.tax.stresstest.StressTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({AccuracyTests.class, FailureTests.class, StressTests.class})
public class AllTests {
  // Integrate all test cases.
}

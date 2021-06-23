package com.pfg.utils;


import com.pfg.utils.accuracytests.AccuracyTests;
import com.pfg.utils.failuretests.FailureTests;
import com.pfg.utils.stresstest.StressTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({AccuracyTests.class, FailureTests.class, StressTests.class})
public class AllTests {
  // Integrate all test cases.
}

package com.niuge.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestCustomerEvent {
  @Autowired
  CustomerEvent customerEvent;

  @Test
  public void test() {
    customerEvent.publishEvent();
  }
}

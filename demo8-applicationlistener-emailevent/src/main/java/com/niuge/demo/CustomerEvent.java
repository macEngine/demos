package com.niuge.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component // 注意这里必须要有Component, 被容器管理起来.
public class CustomerEvent {
  @Autowired
  private ApplicationContext applicationContext;

  public void publishEvent() {
    // 创建一个ApplicationEvent对象
    EmailEvent event = new EmailEvent("hello", "abc@163.com", "This is a test");
    System.out.println();
    System.out.println(applicationContext.getApplicationName());
    System.out.println();
    // 主动触发该事件
    applicationContext.publishEvent(event);
  }
}
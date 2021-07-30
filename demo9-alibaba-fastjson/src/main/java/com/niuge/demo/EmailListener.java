package com.niuge.demo;


import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component // 注意这里必须要有Component, 被容器管理起来.
public class EmailListener implements ApplicationListener {
  public void onApplicationEvent(ApplicationEvent event) {
    if (event instanceof EmailEvent) {
      EmailEvent emailEvent = (EmailEvent) event;
      System.out.println("邮件地址：" + emailEvent.getAddress());
      System.out.println("邮件内容：" + emailEvent.getText());
    } else {
      // System.out.println("容器本身事件：" + event);
    }
  }
}
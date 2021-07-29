package com.niuge.demo;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component // 需对该类进行Bean的实例化
public class ApplicationListenerDemo1 implements ApplicationListener<ContextRefreshedEvent> {
  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    // 打印容器中出事Bean的数量
    System.out.println("监听器获得容器中初始化Bean数量：" + event.getApplicationContext().getBeanDefinitionCount());

    String[] beanDefinitionNames = event.getApplicationContext().getBeanDefinitionNames();

    // 把所有bean的名字都打印出来
    System.out.println();
    for (String name : beanDefinitionNames) {
      System.out.println(name);
    }
    System.out.println();
  }
}

package com.niuge.demos.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
// Spring的Java配置方式是通过 @Configuration 和 @Bean 注解实现的：
// a、@Configuration 作用于类上，相当于一个xml配置文件
// b、@Bean 作用于方法上，相当于xml配置中的<bean>
// @Bean注解将会实例化、配置和初始化一个新对象，这个对象将由Spring的IoC容器来管理。@Bean声明所起到的作用与<bean/> 元素类似。
public class MyConfiguration {
    @Bean(name = "student")
    public Student getStudent() {
        return new Student("1", "name1", Student.Gender.MALE, 100);
    }
}

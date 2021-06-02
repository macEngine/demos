package com.niuge.demos.spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
public class SpringAnnotationTest {
    @Autowired
    private ApplicationContext context;
    @Test
    public void test() {
        Student student = context.getBean("student", Student.class);
        System.out.println(student);
    }
}

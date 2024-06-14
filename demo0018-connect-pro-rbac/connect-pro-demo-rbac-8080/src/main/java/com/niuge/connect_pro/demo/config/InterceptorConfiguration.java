package com.niuge.connect_pro.demo.config;

import com.niuge.connect_pro.demo.auth.AuthenticationInterceptor;
import com.niuge.connect_pro.demo.auth.AuthorizationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class InterceptorConfiguration extends WebMvcConfigurerAdapter {

  @Bean
  public AuthorizationInterceptor authorizationInterceptor() {
    return new AuthorizationInterceptor();
  }

  @Bean
  public AuthenticationInterceptor authenticationInterceptor() {
    return new AuthenticationInterceptor();
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(authenticationInterceptor());
    registry.addInterceptor(authorizationInterceptor());
  }
}

package com.niuge.rocketmq;

public class User {
  private String userName;
  private Byte userAge;
  // 省略 Getter Setter
  @Override
  public String toString() {
    return "User{" +
        "userName='" + userName + '\'' +
        ", userAge=" + userAge +
        '}';
  }
}
package com.pfg.utils.service;

import org.springframework.stereotype.Service;

@Service
public class UserService {
  public String getMobileNumber(Long userId) {
    return "getMobileNumber in service";

  }

  public String login(String mobileNumber, String password) {
    return "login in service";
  }
}

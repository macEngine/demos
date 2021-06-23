package com.pfg.utils.service;

import org.springframework.stereotype.Service;

@Service
public class UserService {
  public String login(String mobileNumber, String password) {
    return mobileNumber + "_" + password;
  }
}

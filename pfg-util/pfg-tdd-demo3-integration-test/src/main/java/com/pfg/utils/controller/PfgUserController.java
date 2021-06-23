package com.pfg.utils.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/pfg/user")
public class PfgUserController {

  @GetMapping(value = "/getMobileNumber")
  public String getMobileNumber() {
    return "18600000000";

  }

  @PostMapping(value = "/login")
  public String login(UserLoginRequest userLoginRequest) {
    return "success";
  }
}

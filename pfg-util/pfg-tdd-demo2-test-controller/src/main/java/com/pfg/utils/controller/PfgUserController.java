package com.pfg.utils.controller;

import com.pfg.utils.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/pfg/user")
public class PfgUserController {

  @Autowired
  private UserService userService;

  @GetMapping(value = "/getMobileNumber")
  public String getMobileNumber() {
    return userService.getMobileNumber(1l);

  }

  @PostMapping(value = "/login")
  public String login(@RequestBody UserLoginRequest userLoginRequest) {
    return userService.login(userLoginRequest.mobileNumber, userLoginRequest.password);
  }
}

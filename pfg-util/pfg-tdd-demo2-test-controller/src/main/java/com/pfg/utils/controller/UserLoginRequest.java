package com.pfg.utils.controller;

import javax.validation.constraints.NotBlank;

public class UserLoginRequest {
  @NotBlank(message = "手机号不能为空")
  public String mobileNumber;
  @NotBlank(message = "密码不能为空")
  public String password;
}

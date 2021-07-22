package com.niuge.cors;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {
    @GetMapping(value = "/getMobileNumber")
    public String getMobileNumber() {
        return "getMobileNumber success";

    }

    @PostMapping(value = "/login")
    public String login(@RequestBody UserLoginRequest userLoginRequest) {
        return "login success";
    }
}

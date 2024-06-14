package com.niuge.connect_pro.demo.auth;

import javax.servlet.http.HttpServletRequest;

public class RequestParser {
    public static String getJWTToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }
}

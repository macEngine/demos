package com.niuge.connect_pro.demo.authmodel;

import lombok.Data;

import java.util.List;

@Data
public class Scope {
    List<String> actions;
    Resource resource;
}

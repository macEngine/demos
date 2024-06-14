package com.niuge.connect_pro.demo.authmodel;

import lombok.Data;

import java.util.List;

@Data
public class Resource {
  ResourceType resourceType;
  List<String> resourceValue;
}

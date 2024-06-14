package com.niuge.connect_pro.demo.model;

import com.niuge.connect_pro.demo.authmodel.Resource;
import lombok.Data;

import java.util.Map;

@Data
public class RequesterVO {
  private String requesterId;
  RequesterParty requesterParty;
  Map<String, Resource> actionAndResourceMap;
}

package com.niuge.demo.a01_pfg.demo1;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;

import java.util.List;

public class A210720_MultiplierPrizeRule {
  @Test
  public void 空串解析float_结果为null() {
    String rule1 = "[{\"level\":1,\"minPoints\":\"1000\",\"maxPoints\":\"\",\"currency\":356,\"multiplier\":\"10\"},{\"level\":2,\"minPoints\":\"700\",\"maxPoints\":\"1000\",\"currency\":356,\"multiplier\":\"7\"},{\"level\":3,\"minPoints\":\"500\",\"maxPoints\":\"700\",\"currency\":356,\"multiplier\":\"5\"},{\"level\":4,\"minPoints\":\"200\",\"maxPoints\":\"500\",\"currency\":356,\"multiplier\":\"2\"},{\"level\":5,\"minPoints\":\"100\",\"maxPoints\":\"200\",\"currency\":356,\"multiplier\":\"1\"}]";
    List<MultiplierPrizeRule> multiplierPrizeRules = JSON.parseArray(rule1, MultiplierPrizeRule.class);
    for (MultiplierPrizeRule multiplierPrizeRule : multiplierPrizeRules) {
      // "maxPoints":"", 解析出来的字段是null，不是0.
      System.out.println(multiplierPrizeRule.getMinPoints() + "  " + multiplierPrizeRule.getMaxPoints());
      // System.out.println(multiplierPrizeRule.getMinPoints() + "  " + Float.valueOf(multiplierPrizeRule.getMaxPoints()));
    }
  }
}

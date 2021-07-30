package com.niuge.demo.a01_pfg.demo1;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MultiplierPrizeRule extends PrizeRule {

  private Float minPoints; // include

  private Float maxPoints; // exclude

  private Float multiplier;
}

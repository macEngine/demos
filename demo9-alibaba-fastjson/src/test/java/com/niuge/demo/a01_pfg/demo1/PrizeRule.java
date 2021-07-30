package com.niuge.demo.a01_pfg.demo1;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @Author yexun
 * @Date 2019-04-12
 */
@ToString
public class PrizeRule {

  private Integer level;

  private Integer rankFrom;

  private Integer rankTo;

  private String prize;

  private Long gratificationId;

  private Integer prizePoolProportion;

  private Integer winners;

  private Integer currency;

  public Long getGratificationId() {
    return gratificationId;
  }

  public PrizeRule setGratificationId(Long gratificationId) {
    this.gratificationId = gratificationId;
    return this;
  }

  public Integer getCurrency() {
    return currency;
  }

  public PrizeRule setCurrency(Integer currency) {
    this.currency = currency;
    return this;
  }

  public Integer getLevel() {
    return level;
  }

  public PrizeRule setLevel(Integer level) {
    this.level = level;
    return this;
  }

  public Integer getRankFrom() {
    return rankFrom;
  }

  public PrizeRule setRankFrom(Integer rankFrom) {
    this.rankFrom = rankFrom;
    return this;
  }

  public Integer getRankTo() {
    return rankTo;
  }

  public PrizeRule setRankTo(Integer rankTo) {
    this.rankTo = rankTo;
    return this;
  }

  public String getPrize() {
    return prize;
  }

  @JSONField(serialize = false)
  public BigDecimal getPrizeValue() {
    return new BigDecimal(prize);
  }

  public PrizeRule setPrize(BigDecimal prize) {
    this.prize = prize.toString();
    return this;
  }

  public PrizeRule setPrize(String prize) {
    this.prize = prize;
    return this;
  }

  public Integer getPrizePoolProportion() {
    return prizePoolProportion;
  }

  public PrizeRule setPrizePoolProportion(Integer prizePoolProportion) {
    this.prizePoolProportion = prizePoolProportion;
    return this;
  }

  public Integer getWinners() {
    return winners;
  }

  public PrizeRule setWinners(Integer winners) {
    this.winners = winners;
    return this;
  }
}

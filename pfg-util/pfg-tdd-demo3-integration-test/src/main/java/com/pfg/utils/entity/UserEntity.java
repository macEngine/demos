package com.pfg.utils.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String mobileNumber;
  private String password;

  public UserEntity() {
  }

  public UserEntity(String mobileNumber, String password) {
    this.mobileNumber = mobileNumber;
    this.password = password;
  }

  public UserEntity(Long id, String mobileNumber) {
    this.id = id;
    this.mobileNumber = mobileNumber;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getMobileNumber() {
    return mobileNumber;
  }

  public void setMobileNumber(String mobileNumber) {
    this.mobileNumber = mobileNumber;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}

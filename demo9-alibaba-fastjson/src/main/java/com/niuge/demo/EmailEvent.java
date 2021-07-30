package com.niuge.demo;


import org.springframework.context.ApplicationEvent;


public class EmailEvent extends ApplicationEvent {
  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  private String address;

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  private String text;

  public EmailEvent(Object source, String address, String text) {
    super(source);
    this.address = address;
    this.text = text;
  }

  public EmailEvent(Object source) {
    super(source);
  }
}
//......address和text的setter、getter
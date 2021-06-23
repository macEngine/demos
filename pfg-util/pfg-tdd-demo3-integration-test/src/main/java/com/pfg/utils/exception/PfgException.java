package com.pfg.utils.exception;

public class PfgException extends RuntimeException {
  private String message;

  public PfgException(String message) {
    super(message);
    this.message = message;
  }
}

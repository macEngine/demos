package com.niuge.connect_pro.demo.model;

public class RequesterThreadLocal {

  static ThreadLocal<RequesterVO> threadLocal = new ThreadLocal<RequesterVO>() {
    public RequesterVO initialValue() {
      return null;
    }
  };


  public static void remove() {
    threadLocal.remove();
  }

  public static void set(RequesterVO requesterVO) {
    threadLocal.set(requesterVO);
  }

  public static RequesterVO get() {
    return threadLocal.get();
  }

  public static String getId() {
    RequesterVO requesterVO = threadLocal.get();
    return requesterVO == null ? "" : requesterVO.getRequesterId();
  }
}

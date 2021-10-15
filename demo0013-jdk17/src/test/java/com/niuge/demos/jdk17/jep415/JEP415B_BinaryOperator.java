package com.niuge.demos.jdk17.jep415;

import org.junit.jupiter.api.Test;

import java.io.ObjectInputStream;
import java.io.*;
import java.util.function.BinaryOperator;

public class JEP415B_BinaryOperator {
  public static class PrintFilterFactory implements BinaryOperator<ObjectInputFilter> {
    @Override
    public ObjectInputFilter apply(
        ObjectInputFilter currentFilter, ObjectInputFilter nextFilter) {

      System.out.println("current:" + currentFilter);
      System.out.println("next:" + nextFilter);

      return ObjectInputFilter.merge(nextFilter, currentFilter);
    }
  }

  @Test
  public void test() throws Exception {
    PrintFilterFactory filterFactory = new PrintFilterFactory();
    ObjectInputFilter.Config.setSerialFilterFactory(filterFactory);

    ObjectInputFilter filter1 = ObjectInputFilter.Config.createFilter(
        "com.niuge.demos.jdk17.*;java.base/*;!*");
    ObjectInputFilter.Config.setSerialFilter(filter1);

    ObjectInputFilter intFilter = ObjectInputFilter.allowFilter(
        cl -> cl.equals(String.class), ObjectInputFilter.Status.REJECTED);

    // if pass anything other than String.class, hit filter status: REJECTED
    byte[] byteStream = convertObjectToStream(99);

    //byte[] byteStream = convertObjectToStream("hello");
    InputStream is = new ByteArrayInputStream(byteStream);
    ObjectInputStream ois = new ObjectInputStream(is);

    ois.setObjectInputFilter(intFilter);

    try {
      Object obj = ois.readObject();
      System.out.println("Read obj: " + obj);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  private static byte[] convertObjectToStream(Object obj) {
    ByteArrayOutputStream boas = new ByteArrayOutputStream();
    try (ObjectOutputStream ois = new ObjectOutputStream(boas)) {
      ois.writeObject(obj);
      return boas.toByteArray();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
    throw new RuntimeException();
  }
}

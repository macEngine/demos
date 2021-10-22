package com.niuge.demos.jdk17.jep324;


import org.junit.jupiter.api.Test;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.NamedParameterSpec;

public class Demo {
  @Test
  public void test() throws Exception {

    KeyPairGenerator kpg = KeyPairGenerator.getInstance("XDH");
    NamedParameterSpec paramSpec = new NamedParameterSpec("X25519");
    kpg.initialize(paramSpec); // equivalent to kpg.initialize(255)
    // alternatively: kpg = KeyPairGenerator.getInstance("X25519")
    KeyPair kp = kpg.generateKeyPair();

    System.out.println("--- Public Key ---");
    PublicKey publicKey = kp.getPublic();

    System.out.println(publicKey.getAlgorithm());   // XDH
    System.out.println(publicKey.getFormat());      // X.509

    // save this public key
    byte[] pubKey = publicKey.getEncoded();

    System.out.println("---");

    System.out.println("--- Private Key ---");
    PrivateKey privateKey = kp.getPrivate();

    System.out.println(privateKey.getAlgorithm());  // XDH
    System.out.println(privateKey.getFormat());     // PKCS#8

    // save this private key
    byte[] priKey = privateKey.getEncoded();
  }
}


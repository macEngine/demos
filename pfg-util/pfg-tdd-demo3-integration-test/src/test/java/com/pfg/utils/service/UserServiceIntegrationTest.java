package com.pfg.utils.service;

import com.pfg.utils.entity.UserEntity;
import com.pfg.utils.repository.UserEntityRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UserServiceIntegrationTest {

  @Autowired
  private UserService userService;
  @Autowired
  private UserEntityRepository userEntityRepository;

  @AfterEach
  public void after() {
    userEntityRepository.deleteAll();
  }

  @Test
  public void givenUserId_whenGetMobileNumber_thenOK() {
    String mobileNumber = "18600000000";
    String password = "123456";
    UserEntity userInDb = userEntityRepository.save(new UserEntity(mobileNumber, password));
    Long userId = userInDb.getId();

    String mobileNumberInDb = userService.getMobileNumber(userId);
    System.out.println(mobileNumberInDb);
    assertEquals(mobileNumberInDb, mobileNumber);
  }

  @Test
  public void givenValidPassword_whenLogin_thenOK() {
    String mobileNumber = "18600000000";
    String password = "123456";
    UserEntity userInDb = userEntityRepository.save(new UserEntity(mobileNumber, password));

    String message = userService.login(mobileNumber, password);
    System.out.println(message);
    assertEquals("success", message);
  }

  @Test
  public void givenInvalidPassword_whenLogin_thenOK() {
    String mobileNumber = "18600000000";
    String password = "123456";
    UserEntity userInDb = userEntityRepository.save(new UserEntity(mobileNumber, password));

    String message = userService.login(mobileNumber, "invalidPassword");
    System.out.println(message);
    assertEquals("error", message);
  }
}


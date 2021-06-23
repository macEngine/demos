package com.pfg.utils.service;

import com.pfg.utils.entity.UserEntity;
import com.pfg.utils.repository.UserEntityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
//@SpringBootTest 也可以，但是加载上下文并初始化所有bean，速度慢。
@Import(UserService.class)
public class UserServiceTest {
  @Autowired
  private UserService userService;
  @MockBean
  private UserEntityRepository userEntityRepository;

  @Test
  public void givenUserId_whenGetMobileNumber_thenOK() {
    String mobileNumber = "18600000000";
    String password = "123456";

    UserEntity userInDb = new UserEntity(mobileNumber, password);
    userInDb.setId(1L);
    Mockito.when(userEntityRepository.findById(userInDb.getId()))
        .thenReturn(Optional.of(userInDb));

    String mobileNumberInDb = userService.getMobileNumber(userInDb.getId());
    System.out.println(mobileNumberInDb);
    assertEquals(mobileNumberInDb, mobileNumber);
  }

  @Test
  public void givenValidPassword_whenLogin_thenOK() {
    String mobileNumber = "18600000000";
    String password = "123456";

    UserEntity userInDb = new UserEntity(mobileNumber, password);
    Mockito.when(userEntityRepository.findByMobileNumber(mobileNumber))
        .thenReturn(userInDb);

    String message = userService.login(mobileNumber, password);
    System.out.println(message);
    assertEquals("success", message);
  }

  @Test
  public void givenInvalidPassword_whenLogin_thenOK() {
    String mobileNumber = "18600000000";
    String password = "123456";
    UserEntity userInDb = new UserEntity(mobileNumber, password);

    Mockito.when(userEntityRepository.findByMobileNumber(mobileNumber))
        .thenReturn(userInDb);


    System.out.println(userService);
    String message = userService.login(mobileNumber, "invalidPassword");
    System.out.println(message);
    assertEquals("error", message);
  }
}


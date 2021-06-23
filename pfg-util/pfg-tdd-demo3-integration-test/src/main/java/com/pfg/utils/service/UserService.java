package com.pfg.utils.service;

import com.pfg.utils.entity.UserEntity;
import com.pfg.utils.exception.PfgException;
import com.pfg.utils.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  @Autowired
  private UserEntityRepository userEntityRepository;

  public String getMobileNumber(Long userId) {
    UserEntity userEntity = userEntityRepository.findById(userId).orElseGet(null);
    if (userEntity == null) {
      throw new PfgException("User does not exist.");
    }
    return userEntity.getMobileNumber();
    // return "18600000000";
  }

  public String login(String mobileNumber, String password) {
    UserEntity userEntity = userEntityRepository.findByMobileNumber(mobileNumber);
    if (userEntity == null) {
      throw new PfgException("User does not exist.");
    }
    if (password.equals(userEntity.getPassword())) {
      return "success";
    } else {
      return "error";
    }
  }
}

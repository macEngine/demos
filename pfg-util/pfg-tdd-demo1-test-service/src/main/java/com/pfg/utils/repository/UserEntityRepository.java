package com.pfg.utils.repository;

import com.pfg.utils.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
  UserEntity findByMobileNumber(String mobileNumber);
}


package com.covid19.alertsystem.dao;

import com.covid19.alertsystem.entity.UserPO;

import java.util.List;

public interface UserDao {
  void saveRegistration(UserPO registrationPO);

  List<UserPO> getAllUsers();
}

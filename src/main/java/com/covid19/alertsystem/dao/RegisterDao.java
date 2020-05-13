package com.covid19.alertsystem.dao;

import com.covid19.alertsystem.entity.RegistrationPO;

public interface RegisterDao {
  void saveRegistration(RegistrationPO registrationPO);
}

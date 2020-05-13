package com.covid19.alertsystem.dao.impl;

import com.covid19.alertsystem.dao.RegisterDao;
import com.covid19.alertsystem.entity.RegistrationPO;
import org.mongodb.morphia.AdvancedDatastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RegisterDaoImpl implements RegisterDao {

  @Autowired
  private AdvancedDatastore advancedDatastore;

  @Override public void saveRegistration(RegistrationPO registrationPO) {
    advancedDatastore.save(registrationPO);
  }
}

package com.covid19.alertsystem.dao.impl;

import com.covid19.alertsystem.dao.UserDao;
import com.covid19.alertsystem.entity.UserPO;
import org.mongodb.morphia.AdvancedDatastore;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

  @Autowired
  private AdvancedDatastore advancedDatastore;

  @Override
  public void saveRegistration(UserPO registrationPO) {
    advancedDatastore.save(registrationPO);
  }

  @Override
  public List<UserPO> getAllUsers() {
    Query<UserPO> query = advancedDatastore.createQuery(UserPO.class);
    return query.asList();
  }

  @Override public List<UserPO> getUsersByAlertPreference(String preference) {
    Query<UserPO> query = advancedDatastore.createQuery(UserPO.class);
    query.field("alert_preference").equal(preference);
    return query.asList();
  }

  @Override public void updateUsers(List<UserPO> users) {
    advancedDatastore.merge(users);
  }
}

package com.covid19.alertsystem.service;

import com.covid19.alertsystem.conversion.RegistrationConvertor;
import com.covid19.alertsystem.dao.UserDao;
import com.covid19.alertsystem.dto.RegistrationRequest;
import com.covid19.alertsystem.entity.UserPO;
import com.covid19.alertsystem.utils.RegistrationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

  @Autowired
  private RegistrationValidator registrationValidator;

  @Autowired
  private RegistrationConvertor registrationConvertor;

  @Autowired
  private UserDao userDao;

  public void register(RegistrationRequest request) throws Exception {
    registrationValidator.validate(request);
    UserPO registrationPO = registrationConvertor.convert(request);
    userDao.saveRegistration(registrationPO);
  }

  public List<UserPO> getAllUsers(){
    return userDao.getAllUsers();
  }

  public List<UserPO> getUsersByPreference(String preference){
    return userDao.getUsersByAlertPreference(preference);
  }

  public void updateUsers(List<UserPO> eligibleUsers) {
    userDao.updateUsers(eligibleUsers);
  }
}

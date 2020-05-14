package com.covid19.alertsystem.service;

import com.covid19.alertsystem.entity.UserPO;
import com.covid19.alertsystem.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlertService {

  @Autowired
  private UserService userService;

  public void handleAlerts(String zipcode) {
    List<UserPO> allUsers = userService.getAllUsers();
    List<UserPO> onCasePreferenceUsers = allUsers.stream().filter(obj -> obj.getAlertPreference().equals(
        Constants.ON_NEW_CASE)).collect(Collectors.toList());
    allUsers.removeAll(onCasePreferenceUsers);



  }
}

package com.covid19.alertsystem.service;

import com.covid19.alertsystem.dao.TotalReportsDao;
import com.covid19.alertsystem.entity.TotalReportsPO;
import com.covid19.alertsystem.entity.UserPO;
import com.covid19.alertsystem.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlertService {

  private static final Integer HOUR =  3600*1000;

  @Autowired
  private UserService userService;

  @Autowired
  private TotalReportsDao totalReportsDao;

  @Autowired
  private SendMessageService sendMessageService;

  public void handleAlerts(String zipcode) {
    List<UserPO> allUsers = userService.getAllUsers();
    List<UserPO> eligibleUsers = allUsers.stream().filter(obj -> (obj.getAlertPreference().equals(
        Constants.ON_NEW_CASE)) || obj.getLastAlertTime() == null).collect(Collectors.toList());
    allUsers.removeAll(eligibleUsers);

    for(UserPO user: allUsers){

      switch(user.getAlertPreference()){
      case "every_day":
        Date lastAlertPlus24Hours = new Date(user.getLastAlertTime().getTime() + 24 * HOUR);
        if(new Date().after(lastAlertPlus24Hours)){
          eligibleUsers.add(user);
        }
        break;

      case "every_two_days":
        Date lastAlertPlus48Hours = new Date(user.getLastAlertTime().getTime() + 48 * HOUR);
        if(new Date().after(lastAlertPlus48Hours)){
          eligibleUsers.add(user);
        }
        break;
      }
    }

    sendAlerts(eligibleUsers);
  }


  private void sendAlerts(List<UserPO> eligibleUsers) {
    if(eligibleUsers.isEmpty())
      return;

    List<TotalReportsPO> totalReports = totalReportsDao.getTotalReports();

    for(UserPO user : eligibleUsers){
      List<TotalReportsPO> eligibleReports = calculateEligibleAlerts(user, totalReports);
      if(eligibleReports.isEmpty()) continue;

      if(user.getIsPhoneNumber()){
        sendMobileAlerts(user, eligibleReports);
      } else {
        sendEmailAlerts(user, eligibleReports);
      }
    }
  }

  private List<TotalReportsPO> calculateEligibleAlerts(UserPO user, List<TotalReportsPO> totalReports) {
    return totalReports.stream()
        .filter(report -> user.getRegisterZipcodes().contains(report.getZipcode()))
        .collect(Collectors.toList());
  }

  private void sendMobileAlerts(UserPO userPO, List<TotalReportsPO> eligibleReports) {
    sendMessageService.sendMessage(userPO, eligibleReports);
  }

  private void sendEmailAlerts(UserPO userPO, List<TotalReportsPO> eligibleReports) {
  }
}

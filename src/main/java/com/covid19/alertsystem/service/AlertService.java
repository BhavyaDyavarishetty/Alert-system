package com.covid19.alertsystem.service;

import com.covid19.alertsystem.dao.TotalReportsDao;
import com.covid19.alertsystem.entity.TotalReportsPO;
import com.covid19.alertsystem.entity.UserPO;
import com.covid19.alertsystem.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

  public void onCaseHandleAlerts(String zipcode) {
    List<UserPO> allUsers = userService.getAllUsers();
    allUsers = allUsers.stream()
        .filter(obj -> (obj.getAlertPreference().equals(Constants.ON_NEW_CASE)) )
        .collect(Collectors.toList());
    sendAlerts(allUsers);
    allUsers.forEach(user -> user.setLastAlertTime(new Date()));
    userService.updateUsers(allUsers);
  }

  @Scheduled(cron = "0 0 9 ? * * *")
  public void handleAlerts() {
    List<UserPO> allUsers = userService.getAllUsers();
    List<UserPO> eligibleUsers = new ArrayList<>();

    for (UserPO user : allUsers) {
      if(user.getLastAlertTime() == null){
        if(!user.getAlertPreference().equals(Constants.ON_NEW_CASE)){
          eligibleUsers.add(user);
        }
        continue;
      }

      switch (user.getAlertPreference()) {
      case "every_day":
        Date lastAlertPlus24Hours = new Date(user.getLastAlertTime().getTime() + 24 * HOUR);
        if (new Date().after(lastAlertPlus24Hours)) {
          eligibleUsers.add(user);
        }
        break;

      case "every_two_days":
        Date lastAlertPlus48Hours = new Date(user.getLastAlertTime().getTime() + 48 * HOUR);
        if (new Date().after(lastAlertPlus48Hours)) {
          eligibleUsers.add(user);
        }
        break;

      case "every_week":
        Date lastAlertPlusOneWeek = new Date(user.getLastAlertTime().getTime() + 7 * 24 * HOUR);
        if (new Date().after(lastAlertPlusOneWeek)) {
          eligibleUsers.add(user);
        }
        break;
      default:
        break;
      }
    }
    sendAlerts(eligibleUsers);
    eligibleUsers.forEach(user -> user.setLastAlertTime(new Date()));
    userService.updateUsers(eligibleUsers);
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

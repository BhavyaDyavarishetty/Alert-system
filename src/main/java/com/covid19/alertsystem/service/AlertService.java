package com.covid19.alertsystem.service;

import com.covid19.alertsystem.dao.TotalReportsDao;
import com.covid19.alertsystem.entity.TotalReportsPO;
import com.covid19.alertsystem.entity.UserPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.covid19.alertsystem.utils.Constants.ON_NEW_CASE;

@Service
public class AlertService {

  private static final Integer HOUR = 3600 * 1000;

  @Autowired private UserService userService;

  @Autowired private TotalReportsDao totalReportsDao;

  @Autowired private SendMessageService sendMessageService;

  @Autowired private ZipcodeService zipcodeService;

  public void onCaseHandleAlerts(String zipcode) {
    List<UserPO> allUsers = userService.getUsersByPreference(ON_NEW_CASE);
    allUsers = zipcodeService.filterUsersByZipcode(zipcode, allUsers);
    if (!allUsers.isEmpty()) {
      sendOnCaseAlerts(allUsers, zipcode);
      allUsers.forEach(user -> user.setLastAlertTime(new Date()));
      userService.updateUsers(allUsers);
    }
  }

  private void sendOnCaseAlerts(List<UserPO> eligibleUsers, String zipcode){
    TotalReportsPO totalReportsPO = totalReportsDao.getTotalReportsByZipcode(zipcode);
    for (UserPO user : eligibleUsers) {
      if (user.getIsPhoneNumber()) {
        sendMobileAlerts(user, Collections.singletonList(totalReportsPO));
      } else {
        sendEmailAlerts(user, Collections.singletonList(totalReportsPO));
      }
    }
  }

  @Scheduled(cron = "0 0 9 ? * * *")
  public void handleAlerts() {
    List<UserPO> allUsers = userService.getAllUsers();
    List<UserPO> eligibleUsers = new ArrayList<>();

    for (UserPO user : allUsers) {
      if (user.getLastAlertTime() == null) {
        if (!user.getAlertPreference().equals(ON_NEW_CASE)) {
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
    if (!eligibleUsers.isEmpty()) {
      sendAlerts(eligibleUsers);
      eligibleUsers.forEach(user -> user.setLastAlertTime(new Date()));
      userService.updateUsers(eligibleUsers);
    }
  }

  private void sendAlerts(List<UserPO> eligibleUsers) {
      List<TotalReportsPO> totalReports = totalReportsDao.getTotalReports();

      for (UserPO user : eligibleUsers) {
        List<TotalReportsPO> eligibleReports = calculateEligibleAlerts(user, totalReports);
        if (eligibleReports.isEmpty())
          continue;

        if (user.getIsPhoneNumber()) {
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

  /**
   * Todo: Needs to be implemented check this\
   */
  private void sendEmailAlerts(UserPO userPO, List<TotalReportsPO> eligibleReports) {
  }
}

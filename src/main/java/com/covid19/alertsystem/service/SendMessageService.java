package com.covid19.alertsystem.service;

import com.covid19.alertsystem.entity.TotalReportsPO;
import com.covid19.alertsystem.entity.UserPO;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SendMessageService {

  @Value("${twilio.accountSid:}")
  protected String accountSid;

  @Value("${twilio.authToken:}")
  protected String authToken;

  @Value("${twilio.phoneNumber:}")
  protected String fromPhoneNumber;

  public void sendMessage(UserPO po, List<TotalReportsPO> eligibleReports) {
    Twilio.init(accountSid, authToken);

    StringBuilder body = new StringBuilder("Hi " + po.getName() + ",");
    body.append(System.getProperty("line.separator"));
    body.append("As per "+ new Date().toString() +",the total covid19 cases for the zipcodes:");
    body.append(System.getProperty("line.separator"));

    for(TotalReportsPO reportsPO : eligibleReports){
      body.append(reportsPO.getZipcode() + " : " + reportsPO.getTotalCases());
      body.append(System.getProperty("line.separator"));
    }
    body.append(System.getProperty("Stay safe :)"));

    Message message = Message.creator(
        new com.twilio.type.PhoneNumber("+18329142536"),
        new com.twilio.type.PhoneNumber(fromPhoneNumber),
        body.toString())
        .create();

    System.out.println(message.getSid());
  }
}

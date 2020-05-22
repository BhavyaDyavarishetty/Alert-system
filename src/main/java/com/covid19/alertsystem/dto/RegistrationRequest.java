package com.covid19.alertsystem.dto;

import lombok.Data;
import java.util.List;

@Data
public class RegistrationRequest {

  private String name;

  private String phoneNumber;

  private String emailAddress;

  private Boolean isPhoneNumber;

  private String state;

  private String zipcode;

  private List<String> registerZipcodes;

  private String alertPreference;

  private Integer range;

}

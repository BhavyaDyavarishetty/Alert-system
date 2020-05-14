package com.covid19.alertsystem.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class RegistrationRequest {

  @NotNull(message = "First name cannot be empty")
  private String firstName;

  private String lastName;

  private Boolean isPhoneNumberProvided;

  private String phoneNumber;

  private String emailAddress;

  @NotNull(message = "Zipcode cannot be empty")
  private String zipcode;

  @NotNull(message = "State cannot be empty")
  private String state;

  private List<String> registerZipcodes;

  private String alertInterval;

}

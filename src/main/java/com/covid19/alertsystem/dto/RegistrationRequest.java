package com.covid19.alertsystem.dto;

import java.util.List;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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

  @NotEmpty(message = "Enter atleast one zipcode to register")
  private List<String> registerZipcodes;

}

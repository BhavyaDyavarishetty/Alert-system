package com.covid19.alertsystem.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ReportCaseRequest {

  @NotNull(message = "Age cannot be empty")
  private Integer age;

  @NotNull(message = "Gender cannot be empty")
  private String gender;

  @NotNull(message = "Zipcode cannot be empty")
  private String zipcode;

  @NotNull(message = "State cannot be empty")
  private String state;
}

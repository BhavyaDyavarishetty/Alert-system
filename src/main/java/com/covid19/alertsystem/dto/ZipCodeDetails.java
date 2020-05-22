package com.covid19.alertsystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ZipCodeDetails {

  @JsonProperty(value = "zip_code")
  private String zipCode;
  private Double distance;
  private String city;
  private String state;
}

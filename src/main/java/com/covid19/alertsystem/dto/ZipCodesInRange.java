package com.covid19.alertsystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ZipCodesInRange {

  @JsonProperty(value = "zip_codes")
  private List<ZipCodeDetails> zipcodes;
}

package com.covid19.alertsystem.entity;

import lombok.Data;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity(value = "caseDetails")
@Data
public class ReportCasePO {

  @Id
  private String id;

  private Integer age;

  private String gender;

  private String state;

  private String zipcode;
}

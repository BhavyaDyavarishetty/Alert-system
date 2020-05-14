package com.covid19.alertsystem.entity;

import lombok.Data;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.io.Serializable;

@Entity(value = "caseDetails")
@Data
public class ReportCasePO implements Serializable {

  @Id
  private String id;

  private Integer age;

  private String gender;

  private String state;

  private String zipcode;
}

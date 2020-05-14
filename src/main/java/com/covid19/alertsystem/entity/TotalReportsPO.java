package com.covid19.alertsystem.entity;

import lombok.Data;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.io.Serializable;
import java.util.Date;

@Entity(value = "totalReports")
@Data
public class TotalReportsPO implements Serializable {

  @Id
  private String id;

  private String zipcode;

  private Long totalCases;

  private Date updatedAt;
}

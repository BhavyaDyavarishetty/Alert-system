package com.covid19.alertsystem.entity;

import lombok.Data;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.io.Serializable;
import java.util.List;

@Entity(value = "userDetails")
@Data
public class RegistrationPO implements Serializable{

  @Id
  private String id;

  private String name;

  private String phoneNumber;

  private String emailAddress;

  private Boolean isPhoneNumber;

  private String state;

  private String zipcode;

  private List<String> registerZipcodes;

  private String alertInterval;


}

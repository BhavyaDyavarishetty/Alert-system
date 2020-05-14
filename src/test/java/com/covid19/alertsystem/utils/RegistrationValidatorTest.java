package com.covid19.alertsystem.utils;

import com.covid19.alertsystem.dto.RegistrationRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class RegistrationValidatorTest {

  @Autowired
  private static RegistrationValidator registrationValidator;

  @BeforeAll
  static void setup(){
    registrationValidator = new RegistrationValidator();
  }

  @Test
  public void testValidate() throws Exception {
    RegistrationRequest request = new RegistrationRequest();
    request.setPhoneNumber("8329142536");
    request.setZipcode("94404");
    request.setState("CA");
    registrationValidator.validate(request);
  }

}
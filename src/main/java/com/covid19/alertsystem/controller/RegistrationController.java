package com.covid19.alertsystem.controller;

import com.covid19.alertsystem.dto.RegistrationRequest;
import com.covid19.alertsystem.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegistrationController {

  @Autowired
  private RegistrationService registrationService;

  @RequestMapping("/")
  public String healthCheck() {
    return "OK";
  }

  @PostMapping(value = "/register")
  public void register(@RequestBody RegistrationRequest request) throws Exception {
    registrationService.register(request);
  }


}

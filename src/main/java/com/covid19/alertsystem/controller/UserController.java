package com.covid19.alertsystem.controller;

import com.covid19.alertsystem.dto.RegistrationRequest;
import com.covid19.alertsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

  @Autowired
  private UserService userService;

  @RequestMapping("/")
  public String healthCheck() {
    return "OK";
  }

  @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
  public void register(@RequestBody RegistrationRequest request) throws Exception {
    userService.register(request);
  }


}

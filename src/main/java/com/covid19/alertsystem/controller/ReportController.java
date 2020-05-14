package com.covid19.alertsystem.controller;

import com.covid19.alertsystem.dto.ReportCaseRequest;
import com.covid19.alertsystem.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportController {

  @Autowired
  private ReportService reportService;

  @PostMapping(value = "/report")
  public void reportCase(@RequestBody ReportCaseRequest request) throws Exception {
    reportService.reportCase(request);
  }
}

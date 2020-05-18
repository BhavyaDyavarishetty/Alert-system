package com.covid19.alertsystem.service;

import com.covid19.alertsystem.dao.ReportDao;
import com.covid19.alertsystem.dao.TotalReportsDao;
import com.covid19.alertsystem.dto.ReportCaseRequest;
import com.covid19.alertsystem.entity.ReportCasePO;
import com.covid19.alertsystem.utils.ReportValidator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

  @Autowired
  private ReportValidator reportValidator;

  @Autowired
  private ReportDao reportDao;

  @Autowired
  private AlertService alertService;

  @Autowired
  private TotalReportsDao totalReportsDao;

  public void reportCase(ReportCaseRequest request) throws Exception {
    reportValidator.validate(request);

    ReportCasePO reportCasePO = new ReportCasePO();
    BeanUtils.copyProperties(request, reportCasePO);
    reportDao.saveReportCase(reportCasePO);

    totalReportsDao.saveOrUpdate(reportCasePO);
    alertService.onCaseHandleAlerts(reportCasePO.getZipcode());
  }
}

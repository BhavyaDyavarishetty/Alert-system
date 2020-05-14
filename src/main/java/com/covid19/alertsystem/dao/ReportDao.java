package com.covid19.alertsystem.dao;

import com.covid19.alertsystem.entity.ReportCasePO;

public interface ReportDao {
  void saveReportCase(ReportCasePO reportCasePO);
}

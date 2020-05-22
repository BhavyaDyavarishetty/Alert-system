package com.covid19.alertsystem.dao;

import com.covid19.alertsystem.entity.ReportCasePO;
import com.covid19.alertsystem.entity.TotalReportsPO;

import java.util.List;

public interface TotalReportsDao {

  void save(TotalReportsPO totalReportsPO);

  void update(String zipcode, Integer cases);

  void saveOrUpdate(ReportCasePO reportCasePO);

  Long getCountByZipcode(String zipcode);

  List<TotalReportsPO> getTotalReports();

  TotalReportsPO getTotalReportsByZipcode(String zipcode);

}

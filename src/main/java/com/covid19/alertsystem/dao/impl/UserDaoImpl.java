package com.covid19.alertsystem.dao.impl;

import com.covid19.alertsystem.dao.ReportDao;
import com.covid19.alertsystem.entity.ReportCasePO;
import org.mongodb.morphia.AdvancedDatastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements ReportDao {

  @Autowired
  private AdvancedDatastore advancedDatastore;

  @Override
  public void saveReportCase(ReportCasePO reportCasePO) {
    advancedDatastore.save(reportCasePO);
  }
}

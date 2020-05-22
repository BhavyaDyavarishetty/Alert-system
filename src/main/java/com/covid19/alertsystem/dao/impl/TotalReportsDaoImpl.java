package com.covid19.alertsystem.dao.impl;

import com.covid19.alertsystem.dao.TotalReportsDao;
import com.covid19.alertsystem.entity.ReportCasePO;
import com.covid19.alertsystem.entity.TotalReportsPO;
import org.mongodb.morphia.AdvancedDatastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.covid19.alertsystem.utils.Constants.TOTAL_CASES;
import static com.covid19.alertsystem.utils.Constants.ZIPCODE;

@Repository
public class TotalReportsDaoImpl implements TotalReportsDao {

  @Autowired
  private AdvancedDatastore dataStore;

  @Override
  public void save(TotalReportsPO totalReportsPO){
    dataStore.save(totalReportsPO);
  }

  @Override
  public void update(String zipcode, Integer cases) {
    Query<TotalReportsPO> query = dataStore.createQuery(TotalReportsPO.class);
    query.field(ZIPCODE).in(Collections.singletonList(zipcode));

    UpdateOperations<TotalReportsPO> operation =
        dataStore.createUpdateOperations(TotalReportsPO.class).inc(TOTAL_CASES, cases);
    dataStore.update(query, operation);
  }

  @Override
  public void saveOrUpdate(ReportCasePO po){
    if(getCountByZipcode(po.getZipcode()) > 0) {
      update(po.getZipcode(), 1);
    } else {
      TotalReportsPO totalReportsPO = new TotalReportsPO();
      totalReportsPO.setTotalCases(1L);
      totalReportsPO.setZipcode(po.getZipcode());
      totalReportsPO.setUpdatedAt(new Date());
      save(totalReportsPO);
    }
  }

  @Override
  public Long getCountByZipcode(String zipcode){
    return dataStore.createQuery(TotalReportsPO.class)
        .field(ZIPCODE)
        .contains(zipcode).count();

  }
  @Override
  public List<TotalReportsPO> getTotalReports(){
    Query<TotalReportsPO> query = dataStore.createQuery(TotalReportsPO.class);
    return query.asList();
  }

  @Override
  public TotalReportsPO getTotalReportsByZipcode(String zipcode){
    Query<TotalReportsPO> query = dataStore.createQuery(TotalReportsPO.class);
    query.filter("zipcode", zipcode);
    return query.get();
  }
}

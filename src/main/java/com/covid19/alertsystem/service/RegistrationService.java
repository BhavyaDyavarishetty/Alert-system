package com.covid19.alertsystem.service;

import com.covid19.alertsystem.conversion.RegistrationConvertor;
import com.covid19.alertsystem.dao.RegisterDao;
import com.covid19.alertsystem.dto.RegistrationRequest;
import com.covid19.alertsystem.entity.RegistrationPO;
import com.covid19.alertsystem.utils.RegistrationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

  @Autowired
  private RegistrationValidator registrationValidator;

  @Autowired
  private RegistrationConvertor registrationConvertor;

  @Autowired
  private RegisterDao registerDao;

  public void register(RegistrationRequest request) throws Exception {
    registrationValidator.validate(request);
    RegistrationPO registrationPO = registrationConvertor.convert(request);
    registerDao.saveRegistration(registrationPO);
  }
}

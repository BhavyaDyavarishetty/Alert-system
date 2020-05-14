package com.covid19.alertsystem.conversion;

import com.covid19.alertsystem.dto.RegistrationRequest;
import com.covid19.alertsystem.entity.RegistrationPO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class RegistrationConvertor {

  public RegistrationPO convert(RegistrationRequest registrationRequest){
    RegistrationPO registrationPO = new RegistrationPO();
    BeanUtils.copyProperties(registrationRequest, registrationPO);
    if(!registrationRequest.getAlertInterval().isEmpty()){
      switch(registrationRequest.getAlertInterval()){

      }
    }
    return registrationPO;
  }

}

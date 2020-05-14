package com.covid19.alertsystem.conversion;

import com.covid19.alertsystem.dto.RegistrationRequest;
import com.covid19.alertsystem.entity.UserPO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class RegistrationConvertor {

  public UserPO convert(RegistrationRequest registrationRequest){
    UserPO registrationPO = new UserPO();
    BeanUtils.copyProperties(registrationRequest, registrationPO);
    return registrationPO;
  }

}

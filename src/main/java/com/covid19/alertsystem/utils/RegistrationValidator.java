package com.covid19.alertsystem.utils;

import com.covid19.alertsystem.dto.RegistrationRequest;
import com.covid19.alertsystem.exceptions.ValidationException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class RegistrationValidator {

  protected static Map<String, String> stateMap;

  @PostConstruct
  public static void init(){
    stateMap = new HashMap<>();
  }

  public void validate(RegistrationRequest request) throws Exception {
    validatePhoneNumber(request.getPhoneNumber());
    validateZipcode(request.getZipcode());
    validateState(request.getState());
  }

  private void validatePhoneNumber(String phoneNumber) throws ValidationException {
//    if(!phoneNumber.matches("\\d(-\\d{3}){2}-\\d{4}")){
//      throw new ValidationException("Invalid phone number");
//    }
  }

  private void validateZipcode(String zipcode) throws ValidationException {
    if(!zipcode.matches("\\d{5}")){
      throw new ValidationException("Invalid phone number");
    }
  }

  private void validateState(String state) throws ValidationException {
//    if(!stateMap.containsKey(state)){
//      throw new ValidationException("Invalid state");
//    }
  }

}

package com.covid19.alertsystem.utils;

import com.covid19.alertsystem.dto.RegistrationRequest;
import com.covid19.alertsystem.exceptions.ObjectNotFoundException;
import com.covid19.alertsystem.exceptions.ValidationException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RegistrationValidator {

  protected static Map<String, String> stateMap;

  @PostConstruct
  public static void init(){
    stateMap = new HashMap<>();
  }

  public void validate(RegistrationRequest request) throws Exception {
    validatePhoneNumber(request.getIsPhoneNumber(), request.getPhoneNumber());
    validateEmailAddress(request.getIsPhoneNumber(), request.getEmailAddress());
    validateZipcode(request.getZipcode());
    validateState(request.getState());
    validateRegisteredZipcodes(request.getRegisterZipcodes());
  }

  private void validatePhoneNumber(Boolean isPhoneNumber, String phoneNumber)
      throws ValidationException, ObjectNotFoundException {
    if(isPhoneNumber && StringUtils.isEmpty(phoneNumber)){
      throw new ObjectNotFoundException("Empty phone number");
    }
    if(!phoneNumber.matches("\\d{10}")){
      throw new ValidationException("Invalid phone number");
    }
  }

  private void validateEmailAddress(Boolean isPhoneNumber, String emailAddress)
      throws ValidationException, ObjectNotFoundException {
    if(!isPhoneNumber && StringUtils.isEmpty(emailAddress)){
      throw new ObjectNotFoundException("Empty email address");
    }
//    if(!emailAddress.matches("\\d{10}")){
//      throw new ValidationException("Invalid email address");
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

  private void validateRegisteredZipcodes(List<String> registerZipcodes) throws ValidationException {
    if(registerZipcodes == null || registerZipcodes.isEmpty()){
      throw new ValidationException("Enter atleast one valid zipcode to get your alerts");
    }
    for(String zipcode: registerZipcodes){
      validateZipcode(zipcode);
    }
  }

}

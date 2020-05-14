package com.covid19.alertsystem.utils;

import com.covid19.alertsystem.dto.ReportCaseRequest;
import com.covid19.alertsystem.exceptions.ValidationException;
import org.springframework.stereotype.Component;

import static com.covid19.alertsystem.utils.Constants.genderValues;

@Component
public class ReportValidator {

  public void validate(ReportCaseRequest request) throws Exception {
    validateAge(request.getAge());
    validateGender(request.getGender());
    validateZipcode(request.getZipcode());
    validateState(request.getState());
  }

  private void validateAge(Integer age) throws ValidationException {
    if(age < 0 || age > 130){
      throw new ValidationException("Invalid Age");
    }
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

  private void validateGender(String gender) throws ValidationException {
    if(!genderValues.contains(gender)){
        throw new ValidationException("Invalid value for gender");
    }
  }

}

package com.covid19.alertsystem.service;

import com.covid19.alertsystem.dto.ZipCodeDetails;
import com.covid19.alertsystem.dto.ZipCodesInRange;
import com.covid19.alertsystem.entity.UserPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ZipcodeService {

  @Value("${zipcode.apiKey:}")
  private String apiKey;

  @Autowired
  private RestTemplate restTemplate;

  private static String URL = "https://www.zipcodeapi.com/rest/%s/radius.%s/%s/%s/miles";

  public String constructUrl(String zipcode, Integer range) {
    return String.format(URL, apiKey, "json", zipcode, range);
  }

  public List<UserPO> filterUsersByZipcode(String zipcode, List<UserPO> users) {
    if(users.isEmpty()) return new ArrayList<>();
    List<UserPO> eligibleUsers = new ArrayList<>();
    for (UserPO user : users) {
      List<String> allZipsInRange = getAllZipcodesInRange(zipcode, user.getRange());

      if (allZipsInRange.contains(user.getZipcode())) {
        eligibleUsers.add(user);
      }
    }
    return eligibleUsers;
  }

  public List<String> getAllZipcodesInRange(String zipcode, Integer range) {
    String url = constructUrl(zipcode, range);
    ZipCodesInRange zipCodesInRange = restTemplate.getForObject(url, ZipCodesInRange.class);

    assert zipCodesInRange != null;
    return zipCodesInRange.getZipcodes().stream().map(ZipCodeDetails::getZipCode).collect(Collectors.toList());
  }

}

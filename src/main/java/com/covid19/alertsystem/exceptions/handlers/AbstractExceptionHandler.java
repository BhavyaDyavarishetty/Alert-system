package com.covid19.alertsystem.exceptions.handlers;

import com.covid19.alertsystem.exceptions.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class AbstractExceptionHandler {

  private static final String STATUS = "status";
  private static final String ERROR = "error";
  private static final String MESSAGE = "message";

  @ExceptionHandler(Exception.class)
  public ModelAndView defaultHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) {
    return buildModel(request, response, HttpStatus.INTERNAL_SERVER_ERROR, "Error", ex.getMessage());
  }

  @ExceptionHandler(ValidationException.class)
  private ModelAndView validationError(HttpServletRequest request, HttpServletResponse response, Exception ex) {
    return buildModel(request, response, HttpStatus.EXPECTATION_FAILED, "Validation error", ex.getMessage());
  }

  private ModelAndView buildModel(HttpServletRequest request, HttpServletResponse response, HttpStatus httpStatus, String error, String message) {
    response.setStatus(httpStatus.value());

    Map<String, Object> values = new HashMap<>();
    values.put(STATUS, httpStatus.value());
    values.put(ERROR, error);
    values.put(MESSAGE, message);
    MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
    ModelAndView modelAndView = new ModelAndView(jsonView, values);
    modelAndView.setStatus(httpStatus);
    return modelAndView;
  }

}

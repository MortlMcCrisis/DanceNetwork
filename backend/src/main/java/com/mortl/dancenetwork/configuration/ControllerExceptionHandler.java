package com.mortl.dancenetwork.configuration;

import javax.ws.rs.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerExceptionHandler {

  @ResponseStatus(HttpStatus.FORBIDDEN)  // 409
  @ExceptionHandler(IllegalAccessException.class)
  public void handleForbidden() {
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)  // 409
  @ExceptionHandler(NotFoundException.class)
  public void handleNotFound() {
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)  // 409
  @ExceptionHandler(IllegalArgumentException.class)
  public void handleBadRequest() {
  }
}
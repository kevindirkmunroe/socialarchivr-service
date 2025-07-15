package com.bronzegiant.socialarchivr.security;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
class UserNotFoundAdvice {

  @ExceptionHandler(InvalidCredentialsException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  String invalidCredentialsHandler(InvalidCredentialsException ex) {
    return ex.getMessage();
  }
}
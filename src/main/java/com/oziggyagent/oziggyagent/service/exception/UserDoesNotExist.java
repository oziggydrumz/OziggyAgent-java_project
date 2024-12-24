package com.oziggyagent.oziggyagent.service.exception;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
@AllArgsConstructor

public class UserDoesNotExist extends Throwable {

     private String message;
private HttpStatus httpStatus;

     @Override
     public String getMessage() {
          return this.message;
     }
}

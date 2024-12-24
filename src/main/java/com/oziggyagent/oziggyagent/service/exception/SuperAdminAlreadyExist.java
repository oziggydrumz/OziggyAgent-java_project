package com.oziggyagent.oziggyagent.service.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
@AllArgsConstructor
public class SuperAdminAlreadyExist extends Throwable {

    private String message;
    private HttpStatus httpStatus;
}

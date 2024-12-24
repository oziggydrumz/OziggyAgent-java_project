package com.oziggyagent.oziggyagent.service.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public class SuperAdminDoesNotExist extends Throwable {
    private String message;
    private HttpStatus httpStatus;
}

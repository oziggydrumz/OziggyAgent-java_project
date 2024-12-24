package com.oziggyagent.oziggyagent.service.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor

public class NoAgentAssignToThisLocation extends Throwable {
    private String message;
    private HttpStatus httpStatus;
}

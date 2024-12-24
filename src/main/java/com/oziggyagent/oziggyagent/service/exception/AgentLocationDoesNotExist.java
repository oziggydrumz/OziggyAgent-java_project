package com.oziggyagent.oziggyagent.service.exception;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
@AllArgsConstructor
public class AgentLocationDoesNotExist extends Throwable {
    private String message;
    private HttpStatus httpStatus;

    @Override
    public String getMessage() {
        return this.getMessage();
    }
}

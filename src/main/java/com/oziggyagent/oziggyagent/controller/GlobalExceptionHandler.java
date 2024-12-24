package com.oziggyagent.oziggyagent.controller;

import com.oziggyagent.oziggyagent.service.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.ALREADY_REPORTED)
    @ExceptionHandler(UserAlreadyExist.class)
    public Object duplicate(UserAlreadyExist oz){

        return oz.getMessage();
    }

@ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserDoesNotExist.class)
    public Object unKnow(UserDoesNotExist oz) {

        return oz.getMessage();
}
@ResponseStatus(HttpStatus.NOT_FOUND)
@ExceptionHandler(RoleDoesNotExist.class)
public  Object unKnow(RoleDoesNotExist oz){

        return oz.getMessage();
}
@ResponseStatus(HttpStatus.ALREADY_REPORTED)
@ExceptionHandler(AgentAlreadyExist.class)

public Object duplicate(AgentAlreadyExist oz){

        return oz.getMessage();
}
@ResponseStatus(HttpStatus.NOT_FOUND)
@ExceptionHandler
public Object unKnow(AgentDoesNotExist oz){
        return oz.getMessage();
}

@ResponseStatus(HttpStatus.NOT_FOUND)
@ExceptionHandler(TokenDoesNotExist.class)
public Object invalidToken(TokenDoesNotExist ox)  {

        return ox.getMessage();
}

@ResponseStatus(HttpStatus.ALREADY_REPORTED)
@ExceptionHandler(LocationAlreadyExist.class)
public Object duplicate(LocationAlreadyExist oz){
        return oz.getMessage();

}
    @ResponseStatus(HttpStatus.ALREADY_REPORTED)
    @ExceptionHandler(RoleAlreadyExist.class)
    public Object duplicate(RoleAlreadyExist oz){
        return oz.getMessage();
    }

}

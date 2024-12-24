package com.oziggyagent.oziggyagent.controller;

import com.oziggyagent.oziggyagent.dto.MessageRequest;
import com.oziggyagent.oziggyagent.dto.requestdto.*;
import com.oziggyagent.oziggyagent.service.UserService;
import com.oziggyagent.oziggyagent.service.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping (value="/user",produces = {MediaType.APPLICATION_JSON_VALUE})
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;

    }

    @PostMapping(value = "/userRegister")
    public ResponseEntity<?> userRegister(@RequestBody UserRegisterRequest registerRequest) throws UserAlreadyExist, RoleDoesNotExist {
        return new ResponseEntity<>(userService.register(registerRequest), HttpStatus.CREATED);
    }



    @PostMapping(value = "/confirmation")
    public ResponseEntity<?> confirmation(@RequestBody OtpRequest otpRequest) throws UserAlreadyExist, TokenDoesNotExist, UserDoesNotExist {
        return new ResponseEntity<>(userService.confirmation(otpRequest), HttpStatus.OK);

    }

    @PostMapping(value = "/login")
    public ResponseEntity<?>login(@RequestBody AuthenticationRequest authenticationRequest){
        return new ResponseEntity<>(userService.login(authenticationRequest),HttpStatus.OK);
    }








    @GetMapping(value = "/findUser")
    public ResponseEntity<?>findUser(@RequestBody UserFindRequest userFindRequest) throws UserDoesNotExist {
        return new ResponseEntity<>(userService.findUser(userFindRequest),HttpStatus.OK);


    }

    @GetMapping(value = "/searchLocation")
    public ResponseEntity<?>searchLocation(@RequestBody LocationRequest locationRequest, @RequestParam long id) throws AgentLocationDoesNotExist, LocationDoesNotExist, UserDoesNotExist, AgentDoesNotExist {

        return new ResponseEntity<>(userService.searchLocation(locationRequest,id),HttpStatus.OK);
    }

    @GetMapping(value = "/messageAgent")
    public ResponseEntity<?>messageAgent(@RequestParam("agentId") Long agentId,
                                         @RequestParam("userId") Long userId,
                                         @RequestBody MessageRequest messageRequest) throws AgentDoesNotExist, UserDoesNotExist {
        return new ResponseEntity<>(userService.sendMessage(agentId,userId,messageRequest),HttpStatus.OK);
    }

}

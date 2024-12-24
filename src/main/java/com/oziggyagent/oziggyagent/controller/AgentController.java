package com.oziggyagent.oziggyagent.controller;

import com.oziggyagent.oziggyagent.dto.AgentRegisterRequest;
import com.oziggyagent.oziggyagent.dto.MessageRequest;
import com.oziggyagent.oziggyagent.dto.requestdto.AuthenticationRequest;
import com.oziggyagent.oziggyagent.dto.requestdto.OtpRequest;
import com.oziggyagent.oziggyagent.service.AgentService;
import com.oziggyagent.oziggyagent.service.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/Agent",produces = MediaType.APPLICATION_JSON_VALUE)
public class AgentController {
  private final AgentService agentService;

    public AgentController(AgentService agentService) {
        this.agentService = agentService;
    }

    @PostMapping(value = "/registerAgent")
    public ResponseEntity<?>registerAgent(@RequestBody AgentRegisterRequest agentRegisterRequest) throws SuperAdminDoesNotExist, LocationAlreadyExist, LocationDoesNotExist, AgentAlreadyExist {
        return  new ResponseEntity<>(agentService.registerAgent(agentRegisterRequest), HttpStatus.CREATED);
    }

    @PostMapping(value = "/confirmation")
    public ResponseEntity<?>confirmation(@RequestBody OtpRequest otpRequest) throws AgentDoesNotExist, AccountHasNotBeenConfirmed {
        return new ResponseEntity<>(agentService.confirm(otpRequest),HttpStatus.OK);
    }
    @PostMapping(value = "/login")
    public ResponseEntity<?>login(AuthenticationRequest authenticationRequest){
        return new ResponseEntity<>(agentService.login(authenticationRequest),HttpStatus.OK);

    }

    @PostMapping(value = "/findAgent")
    public ResponseEntity<?>findAgent(@RequestBody String emailAddress) throws AgentDoesNotExist {
        return new ResponseEntity<>(agentService.findAgent(emailAddress),HttpStatus.FOUND);
    }




    @PostMapping(value = "/messageUser")
    public ResponseEntity<?>messageUser( @RequestBody long userId, long agentId, MessageRequest messageRequest) throws UserDoesNotExist {
        return new ResponseEntity<>( agentService.messageUser(userId,agentId,messageRequest),HttpStatus.OK);
    }




}

package com.oziggyagent.oziggyagent.service;

import com.oziggyagent.oziggyagent.dto.AgentRegisterRequest;
import com.oziggyagent.oziggyagent.dto.MessageRequest;
import com.oziggyagent.oziggyagent.dto.requestdto.AuthenticationRequest;
import com.oziggyagent.oziggyagent.dto.requestdto.OtpRequest;
import com.oziggyagent.oziggyagent.dto.requestdto.responesedto.AuthenticationResponse;
import com.oziggyagent.oziggyagent.model.Agent;
import com.oziggyagent.oziggyagent.model.MyLocation;
import com.oziggyagent.oziggyagent.service.exception.*;
import org.springframework.http.ResponseEntity;

public interface AgentService {

    AuthenticationResponse registerAgent(AgentRegisterRequest agentRegisterRequest) throws AgentAlreadyExist, SuperAdminDoesNotExist, LocationDoesNotExist, LocationAlreadyExist;

    String confirm(OtpRequest otpRequest) throws AgentDoesNotExist, AccountHasNotBeenConfirmed;

    AuthenticationResponse login(AuthenticationRequest authenticationRequest );

    Agent findAgent(String emailAddress) throws AgentDoesNotExist;


    ResponseEntity<?>searchLocation(long userId, MyLocation location) throws NoAgentAssignToThisLocation, UserDoesNotExist;

    ResponseEntity<?>messageUser(long userId, long agentId, MessageRequest messageRequest) throws UserDoesNotExist;
}

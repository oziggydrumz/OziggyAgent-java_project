package com.oziggyagent.oziggyagent.service;

import com.oziggyagent.oziggyagent.dto.MessageRequest;
import com.oziggyagent.oziggyagent.dto.requestdto.LocationRequest;
import com.oziggyagent.oziggyagent.dto.requestdto.OtpRequest;
import com.oziggyagent.oziggyagent.dto.requestdto.UserFindRequest;
import com.oziggyagent.oziggyagent.dto.requestdto.UserRegisterRequest;
import com.oziggyagent.oziggyagent.dto.requestdto.responesedto.AuthenticationResponse;
import com.oziggyagent.oziggyagent.model.User;
import com.oziggyagent.oziggyagent.service.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service



public interface UserService {

    AuthenticationResponse register(UserRegisterRequest registerRequest) throws UserAlreadyExist, RoleDoesNotExist;

    String confirmation(OtpRequest request) throws UserAlreadyExist, TokenDoesNotExist, UserDoesNotExist;
    AuthenticationResponse login(com.oziggyagent.oziggyagent.dto.requestdto.AuthenticationRequest authenticationRequest);
    User findUser(UserFindRequest userFindRequest) throws UserDoesNotExist;

    ResponseEntity<?>sendMessage(long agentId, long userId, MessageRequest messageRequest) throws AgentDoesNotExist, UserDoesNotExist;








    Map<String,Object> searchLocation(LocationRequest locationRequest, long id) throws AgentLocationDoesNotExist, LocationDoesNotExist, UserDoesNotExist, AgentDoesNotExist;

}


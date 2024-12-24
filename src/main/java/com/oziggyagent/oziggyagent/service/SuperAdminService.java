package com.oziggyagent.oziggyagent.service;

import com.oziggyagent.oziggyagent.dto.requestdto.OtpRequest;
import com.oziggyagent.oziggyagent.dto.requestdto.SuperAdminRegisterRequest;
import com.oziggyagent.oziggyagent.dto.requestdto.responesedto.AuthenticationResponse;
import com.oziggyagent.oziggyagent.model.MyRoles;
import com.oziggyagent.oziggyagent.model.SuperAdmin;
import com.oziggyagent.oziggyagent.service.exception.*;

public interface SuperAdminService {
    AuthenticationResponse register(SuperAdminRegisterRequest registerRequest) throws SuperAdminDoesNotExist, SuperAdminAlreadyExist;
    String confirmation(OtpRequest otpRequest) throws SuperAdminDoesNotExist, AccountHasNotBeenConfirmed;
    AuthenticationResponse login(com.oziggyagent.oziggyagent.dto.requestdto.AuthenticationRequest authenticationRequest) throws SuperAdminDoesNotExist;
    SuperAdmin findSuperAdmin(SuperAdminRegisterRequest superAdminRegisterRequest) throws SuperAdminDoesNotExist;
    String delete(long id) throws SuperAdminDoesNotExist;
     MyRoles superAdminMakeUserAdmin(long id) throws RoleDoesNotExist, UserDoesNotExist;
    String deleteUser(long id) throws UserDoesNotExist;
    String deleteAgent(long id) throws AgentDoesNotExist;



}

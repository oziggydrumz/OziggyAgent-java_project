package com.oziggyagent.oziggyagent.controller;

import com.oziggyagent.oziggyagent.dto.requestdto.AuthenticationRequest;
import com.oziggyagent.oziggyagent.dto.requestdto.OtpRequest;
import com.oziggyagent.oziggyagent.dto.requestdto.SuperAdminRegisterRequest;
import com.oziggyagent.oziggyagent.service.SuperAdminService;
import com.oziggyagent.oziggyagent.service.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/superAdmin",produces ={MediaType.APPLICATION_JSON_VALUE})
public class SuperAdminController {
    private final SuperAdminService superAdminService;

    public SuperAdminController(SuperAdminService superAdminService) {
        this.superAdminService = superAdminService;
    }
    @RequestMapping(value = "/register")
    public ResponseEntity<?>register(@RequestBody SuperAdminRegisterRequest request) throws SuperAdminDoesNotExist, SuperAdminAlreadyExist {
        return new ResponseEntity<>( superAdminService.register(request), HttpStatus.CREATED);



    }

    @RequestMapping(value = "/confirmation")
    public ResponseEntity<?>confirmation(@RequestBody OtpRequest request) throws SuperAdminDoesNotExist, AccountHasNotBeenConfirmed {
        return new ResponseEntity<>(superAdminService.confirmation(request),HttpStatus.CREATED);

    }
    @RequestMapping(value = "/login")
    public ResponseEntity<?>login(AuthenticationRequest authenticationRequest) throws SuperAdminDoesNotExist {
        return new ResponseEntity<>(superAdminService.login(authenticationRequest),HttpStatus.OK);
    }

    @GetMapping(value = "/findSuperAdmin")
    public ResponseEntity<?>findSuperAdmin(SuperAdminRegisterRequest request) throws SuperAdminDoesNotExist {
        return new ResponseEntity<>(superAdminService.findSuperAdmin(request),HttpStatus.FOUND);
    }

    @DeleteMapping(value = "/deleteSuperAdmin")
    @PreAuthorize("hasAuthority('superAdmin')")
    public ResponseEntity<?>deleteAdmin(@RequestParam long id) throws SuperAdminDoesNotExist {
        return new ResponseEntity<>(superAdminService.delete(id),HttpStatus.OK);
    }
    @DeleteMapping(value = "/deleteUser")
    //@PreAuthorize("hasAnyAuthority('role_superAdministrator')")
    public ResponseEntity<?>deleteUser(long id) throws UserDoesNotExist {
        return new ResponseEntity<>(superAdminService.deleteUser(id),HttpStatus.OK);
    }
    @DeleteMapping(value = "/deleteAgent")
    public ResponseEntity<?>deleteAgent(long id) throws AgentDoesNotExist {
        return new ResponseEntity<>(superAdminService.deleteAgent(id),HttpStatus.OK);


    }
    @RequestMapping(value = "/SuperAdminMakeUserAdmin")
    public ResponseEntity<?>superAdminMakeUserAdmin(long id) throws RoleDoesNotExist, UserDoesNotExist {
        return new ResponseEntity<>(superAdminService.superAdminMakeUserAdmin(id),HttpStatus.OK);
    }


}

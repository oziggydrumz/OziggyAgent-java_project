package com.oziggyagent.oziggyagent.controller;

import com.oziggyagent.oziggyagent.dto.requestdto.RoleRequest;
import com.oziggyagent.oziggyagent.service.RoleService;
import com.oziggyagent.oziggyagent.service.exception.RoleAlreadyExist;
import com.oziggyagent.oziggyagent.service.exception.RoleDoesNotExist;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/MyRoles",produces = {MediaType.APPLICATION_JSON_VALUE})
public class RoleController {
    private final RoleService roleService;

    public RoleController( RoleService roleService) {
        this.roleService = roleService;
    }

    @RequestMapping(value = "/addRoles")
    public ResponseEntity<?>addRoles(@RequestBody RoleRequest roleRequest) throws RoleAlreadyExist {
        return new ResponseEntity<>(roleService.addRole(roleRequest), HttpStatus.CREATED);
    }
    @RequestMapping(value = "/deleteRole")
    public ResponseEntity<?>deleteRole(@PathParam("deleteRole") long id) throws RoleDoesNotExist {
        return new ResponseEntity<>(roleService.deleteRole(id),HttpStatus.OK);

    }

}

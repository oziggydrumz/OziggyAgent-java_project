package com.oziggyagent.oziggyagent.service;

import com.oziggyagent.oziggyagent.dto.requestdto.RoleRequest;
import com.oziggyagent.oziggyagent.model.MyRoles;
import com.oziggyagent.oziggyagent.service.exception.RoleAlreadyExist;
import com.oziggyagent.oziggyagent.service.exception.RoleDoesNotExist;
import org.springframework.stereotype.Service;

@Service
public interface RoleService {
    MyRoles addRole(RoleRequest roleRequest) throws RoleAlreadyExist;
    String deleteRole(long id) throws RoleDoesNotExist;
}

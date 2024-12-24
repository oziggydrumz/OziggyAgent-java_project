package com.oziggyagent.oziggyagent.serviceImp;

import com.oziggyagent.oziggyagent.dto.requestdto.RoleRequest;
import com.oziggyagent.oziggyagent.model.MyRoles;
import com.oziggyagent.oziggyagent.repo.RoleRepo;
import com.oziggyagent.oziggyagent.service.RoleService;
import com.oziggyagent.oziggyagent.service.exception.RoleAlreadyExist;
import com.oziggyagent.oziggyagent.service.exception.RoleDoesNotExist;
import com.oziggyagent.oziggyagent.util.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.util.Optional;
@Service

public class RoleServiceImp implements RoleService {
    @Autowired
    private final RoleRepo roleRepo;
    private final RoleMapper roleMapper;

    public RoleServiceImp(RoleRepo roleRepo, RoleMapper roleMapper) {
        this.roleRepo = roleRepo;
        this.roleMapper = roleMapper;
    }

    @Override
    public MyRoles addRole(RoleRequest roleRequest) throws RoleAlreadyExist {



     Optional<MyRoles> addRoles=roleRepo.findByTitle(roleRequest.getTitle());

     if(addRoles.isPresent())
         throw new RoleAlreadyExist("Role Already Exist", HttpStatus.ALREADY_REPORTED);

        MyRoles roles=roleMapper.map(roleRequest);

     roleRepo.save(roles);

        return roles;
    }

    @Override
    public String deleteRole(long id) throws RoleDoesNotExist {
        MyRoles deleteRole=roleRepo.findById(id);
        if (deleteRole==null){
            throw new RoleDoesNotExist("the role you are trying to delete does not exist",HttpStatus.NOT_FOUND);
        }
        deleteRole.setTitle(null);


        roleRepo.delete(deleteRole);
        return "successfully deleted";

    }

}

package com.oziggyagent.oziggyagent.util.mapper;

import com.oziggyagent.oziggyagent.dto.requestdto.RoleRequest;
import com.oziggyagent.oziggyagent.model.MyRoles;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class RoleMapper {


    public MyRoles map(RoleRequest roleRequest){

        MyRoles roles =new MyRoles(1,"vb",new Date(3),new Date(4));

        roles.setTitle(roleRequest.getTitle());

      return roles ;
    }
}

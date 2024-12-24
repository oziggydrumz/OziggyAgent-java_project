package com.oziggyagent.oziggyagent.util.mapper;

import com.oziggyagent.oziggyagent.dto.requestdto.SuperAdminRegisterRequest;
import com.oziggyagent.oziggyagent.dto.requestdto.responesedto.AuthenticationResponse;
import com.oziggyagent.oziggyagent.model.SuperAdmin;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Builder

public class SuperAdminMapper {
    private SuperAdmin superAdmin;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public SuperAdmin map(SuperAdminRegisterRequest adminRegisterRequest){
        superAdmin.setFirstname(adminRegisterRequest.getFirstname());
        superAdmin.setLastname(adminRegisterRequest.getLastname());
        superAdmin.setPhoneNumber(adminRegisterRequest.getPhoneNumber());
        superAdmin.setEmailAddress(adminRegisterRequest.getEmailAddress());
        superAdmin.setPassword(passwordEncoder.encode(adminRegisterRequest.getPassword()));
        return superAdmin;
    }
}

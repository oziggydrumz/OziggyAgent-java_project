package com.oziggyagent.oziggyagent.util.mapper;

import com.oziggyagent.oziggyagent.dto.requestdto.UserRegisterRequest;
import com.oziggyagent.oziggyagent.dto.requestdto.responesedto.AuthenticationResponse;
import com.oziggyagent.oziggyagent.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@RequiredArgsConstructor
@Component
@Builder

public class RegisterMapper {
    @Autowired
    private PasswordEncoder passwordEncoder;
   // private AuthenticationResponse authenticationResponse;
    private User user;


    public AuthenticationResponse map(UserRegisterRequest userRegisterRequest){
       user.setFirstName(userRegisterRequest.getFirstName());
       user.setLastName(userRegisterRequest.getLastName());
       user.setEmailAddress(userRegisterRequest.getEmailAddress());
       user.setPhoneNumber(userRegisterRequest.getPhoneNumber());
       user.setPassword(passwordEncoder.encode(userRegisterRequest.getPassword()));

       return AuthenticationResponse.builder().build();
    }


}

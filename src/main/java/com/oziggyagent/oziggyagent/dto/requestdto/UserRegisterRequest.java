package com.oziggyagent.oziggyagent.dto.requestdto;

import com.oziggyagent.oziggyagent.model.Agent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Data

@AllArgsConstructor



@Builder
public class UserRegisterRequest {
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;
    private String password;
    private String location;
   // private Agent agent;
}

package com.oziggyagent.oziggyagent.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Data
//@Builder
@AllArgsConstructor


public class AgentRegisterRequest {
    private String firstname;
    private String lastname;
    private  String emailAddress;
    private String phoneNumber;
    private String password;
    private String location;
}

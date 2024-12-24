package com.oziggyagent.oziggyagent.dto.requestdto;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String emailAddress;
    private String password;
}

package com.oziggyagent.oziggyagent.dto.requestdto;

import lombok.Data;

@Data
public class SuperAdminRegisterRequest {
    private String firstname;
    private String lastname;
    private String phoneNumber;
    private String emailAddress;
    private String password;
}

package com.oziggyagent.oziggyagent.service;

import com.oziggyagent.oziggyagent.dto.requestdto.UserLoginRequest;
import com.oziggyagent.oziggyagent.dto.requestdto.UserRegisterRequest;
import com.oziggyagent.oziggyagent.model.OtpValidation;

public interface UserService {
    UserService register(UserRegisterRequest registerRequest);
    UserService login(UserLoginRequest loginRequest);
    UserService findUser(String emailAddress);
    String deleteUser(long id);
    OtpValidation confirmation(String token);

}

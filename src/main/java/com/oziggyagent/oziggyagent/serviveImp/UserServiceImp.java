package com.oziggyagent.oziggyagent.serviveImp;

import com.oziggyagent.oziggyagent.dto.requestdto.UserLoginRequest;
import com.oziggyagent.oziggyagent.dto.requestdto.UserRegisterRequest;
import com.oziggyagent.oziggyagent.model.OtpValidation;
import com.oziggyagent.oziggyagent.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Data
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    @Override
    public UserService register(UserRegisterRequest registerRequest) {
        return null;
    }

    @Override
    public UserService login(UserLoginRequest loginRequest) {
        return null;
    }

    @Override
    public UserService findUser(String emailAddress) {
        return null;
    }

    @Override
    public String deleteUser(long id) {
        return null;
    }

    @Override
    public OtpValidation confirmation(String token) {
        return null;
    }
}

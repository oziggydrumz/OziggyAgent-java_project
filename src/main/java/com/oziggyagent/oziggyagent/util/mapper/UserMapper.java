package com.oziggyagent.oziggyagent.util.mapper;

import com.oziggyagent.oziggyagent.dto.requestdto.UserRegisterRequest;
import com.oziggyagent.oziggyagent.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


@Component
public class UserMapper {
    @Autowired
    private PasswordEncoder passwordEncoder;





    public User map(UserRegisterRequest userRegisterRequest) {
        User user = new User();
        user.setFirstName(userRegisterRequest.getFirstName());
        user.setLastName(userRegisterRequest.getLastName());
        user.setEmailAddress(userRegisterRequest.getEmailAddress());
        user.setPhoneNumber(userRegisterRequest.getPhoneNumber());
        user.setLocation(userRegisterRequest.getLocation());
        // user.setPassword(passwordEncoder.encode(userRegisterRequest.getPassword()));


        return user;


    }
}

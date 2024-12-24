package com.oziggyagent.oziggyagent.util.mapper;

import com.oziggyagent.oziggyagent.dto.AgentRegisterRequest;
import com.oziggyagent.oziggyagent.model.Agent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatterBuilder;
@Component
public class AgentMapper {


    @Autowired
    private PasswordEncoder passwordEncoder;

    public Agent  map(AgentRegisterRequest agentRegisterRequest){
        Agent agent=new Agent();
        agent.setFirstname(agentRegisterRequest.getFirstname());
        agent.setLastname(agentRegisterRequest.getLastname());
        agent.setEmailAddress(agentRegisterRequest.getEmailAddress());
        agent.setPhoneNumber(agentRegisterRequest.getPhoneNumber());
        agent.setPassword(passwordEncoder.encode(agentRegisterRequest.getPassword()));
        return agent;
    }

}

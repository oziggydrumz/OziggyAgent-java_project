package com.oziggyagent.oziggyagent.repo;

import com.oziggyagent.oziggyagent.model.Agent;
import com.oziggyagent.oziggyagent.model.MyLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AgentRepo extends JpaRepository<Agent,Long> {
    List<Agent> findByAgentLocations(MyLocation location);





    Agent findAgentById(Long agentId);
    Agent findAgentByEmailAddress(String emailAddress);


    Agent findAgentByPhoneNumber(String phoneNumber);
}

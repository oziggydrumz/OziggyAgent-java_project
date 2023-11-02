package com.oziggyagent.oziggyagent.repo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AgentRepo extends JpaRepository<AgentRepo,Long> {
    AgentRepo findByEmailAddress(String email);
    AgentRepo findByPhoneNumber(String phoneNumber);
}

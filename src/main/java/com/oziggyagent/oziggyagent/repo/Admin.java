package com.oziggyagent.oziggyagent.repo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Admin extends JpaRepository<Admin,Long> {
    Admin findByEmailAddress(String emailAddress);
}

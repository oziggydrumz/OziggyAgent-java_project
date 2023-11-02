package com.oziggyagent.oziggyagent.repo;

import com.oziggyagent.oziggyagent.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {
    User findUserByEmailAddress(String email);
}

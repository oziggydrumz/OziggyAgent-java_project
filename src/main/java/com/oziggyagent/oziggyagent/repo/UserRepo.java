package com.oziggyagent.oziggyagent.repo;

import com.oziggyagent.oziggyagent.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface UserRepo extends JpaRepository<User,Long> {
    User findUserByEmailAddress(String emailAddress);
    User findAUserById(long id);
    String deleteUserById(long id);
}

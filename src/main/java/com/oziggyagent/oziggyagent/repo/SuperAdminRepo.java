package com.oziggyagent.oziggyagent.repo;

import com.oziggyagent.oziggyagent.model.SuperAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SuperAdminRepo extends JpaRepository<com.oziggyagent.oziggyagent.model.SuperAdmin,Long> {

    SuperAdmin findByEmailAddress(String  emailAddress);


    SuperAdmin findById(long id);
}

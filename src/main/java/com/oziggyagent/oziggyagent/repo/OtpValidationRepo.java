package com.oziggyagent.oziggyagent.repo;

import com.oziggyagent.oziggyagent.model.Agent;
import com.oziggyagent.oziggyagent.model.OtpValidation;
import com.oziggyagent.oziggyagent.model.SuperAdmin;
import com.oziggyagent.oziggyagent.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpValidationRepo extends JpaRepository<OtpValidation,Long> {
    OtpValidation findByToken(String token);
    OtpValidation findByAgent(Agent agent);

    OtpValidation findBySuperAdmin(SuperAdmin superAdmin) ;

    OtpValidation findByUser(User user);
}

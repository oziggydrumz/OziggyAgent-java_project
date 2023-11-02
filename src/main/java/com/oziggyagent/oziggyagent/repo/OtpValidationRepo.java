package com.oziggyagent.oziggyagent.repo;

import com.oziggyagent.oziggyagent.model.OtpValidation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpValidationRepo extends JpaRepository<OtpValidation,Long> {
    OtpValidation findByToken(String token);
}

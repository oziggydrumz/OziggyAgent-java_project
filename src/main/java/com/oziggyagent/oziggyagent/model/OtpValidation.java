package com.oziggyagent.oziggyagent.model;

import com.oziggyagent.oziggyagent.util.mapper.otpGenerator.OtpGenerator;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class OtpValidation {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

        private String token;
        private LocalDateTime createdAt;
        private LocalDateTime expireAt;
        private LocalDateTime confirmAt;

        @OneToOne
        private User user;


        @OneToOne//(cascade = CascadeType.ALL)
      // @JoinColumn(name = "agent_id")
        private Agent agent;

        @OneToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "super_admin_id")
        private SuperAdmin superAdmin;

    public OtpValidation(OtpGenerator generator) {
    }
}

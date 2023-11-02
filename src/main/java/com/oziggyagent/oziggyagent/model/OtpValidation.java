package com.oziggyagent.oziggyagent.model;

import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.time.LocalDateTime;
@Entity

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

       private Agent agent;

       private Admin admin;
}

package com.oziggyagent.oziggyagent.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class HousePlan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  long id;
    private String basic;
    private String standard;
    private String premium;
}

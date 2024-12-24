package com.oziggyagent.oziggyagent.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder

@AllArgsConstructor

@Entity
@Table

public class MyRoles {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String title;

    @CreationTimestamp
    @Temporal(TemporalType.TIME)
    @Column(name = "created_on", updatable = false, nullable = false )
    private Date createdOn;


    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_on",updatable = false,nullable = false)
    private Date updatedOn;



}


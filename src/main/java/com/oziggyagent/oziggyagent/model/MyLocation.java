package com.oziggyagent.oziggyagent.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.naming.Name;
import java.util.*;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table
@Builder
public class MyLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String state;

 @CreationTimestamp
 @Temporal(TemporalType.TIME)
 @Column(name = "created_on",updatable = false,nullable = false)
    private Date created_on;

 @UpdateTimestamp
    @Temporal(TemporalType.TIME)
    @Column(name = "updated_on",updatable = false,nullable = false)
    private Date updated_on;

}
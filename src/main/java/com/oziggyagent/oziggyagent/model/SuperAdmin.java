package com.oziggyagent.oziggyagent.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.ComponentScan;

import java.util.HashSet;
import java.util.Set;

//@RequiredArgsConstructor
//@AllArgsConstructor
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class SuperAdmin  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String firstname;
    private boolean enable=false;
    private String lastname;
    private String emailAddress;
    private String phoneNumber;
    private String password;
    @ManyToOne
    private MyRoles appRole;

    public void addSuperAdmin(MyRoles roles) {
        this.appRole.setTitle(roles.getTitle());
    }

    // public void addSuperAdmin(MyRoles role){

     //   this.appRole.;
   // }


}

package com.oziggyagent.oziggyagent.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class User implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
     private long id;

    private boolean enable=false;
    
    private String password;


    private String FirstName;
    private String lastName;

    private String phoneNumber;
    private String emailAddress;

    @ElementCollection(fetch = FetchType.EAGER)
    private List <AppRole> appRole=new ArrayList<>();

    public User(long id,
                String  emailAddress,
                String password,
                Collection<? extends GrantedAuthority> authority ){

    }





    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

    String name =null;
        for (AppRole role:appRole) {
            name=role.name();

        }

        return List.of(new SimpleGrantedAuthority(appRole+name));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return emailAddress;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enable;
    }
}

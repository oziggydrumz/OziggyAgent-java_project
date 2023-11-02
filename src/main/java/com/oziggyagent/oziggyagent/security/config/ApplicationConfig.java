package com.oziggyagent.oziggyagent.security.config;

import com.oziggyagent.oziggyagent.model.Admin;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@RequiredArgsConstructor

public class ApplicationConfig {

    private final static List<UserDetails>Application_Users= Arrays.asList(

            new User(
                    "ffgfgfgf",
                    "dgdgdgdgd",
                    Collections.singletonList(new SimpleGrantedAuthority("Role user"))
            ),
            new User(
                    "dfdfdfddfd",
                    "dgdhdghdgd",
                    Collections.singletonList(new SimpleGrantedAuthority("Role user"))


            )

    );


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();

    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
return authenticationProvider;



    }







    @Bean
    public UserDetailsService userDetailsService(){
      return new UserDetailsService() {
          @Override
          public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {
              return Application_Users
                      .stream()
                      .filter(u->u.getUsername().equals(emailAddress))
                      .findFirst()
                      .orElseThrow(()->new UsernameNotFoundException("no user with this email"));
          }
      };
    }
}

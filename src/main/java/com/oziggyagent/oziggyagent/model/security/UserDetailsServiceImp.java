package com.oziggyagent.oziggyagent.model.security;

import com.oziggyagent.oziggyagent.model.User;
import com.oziggyagent.oziggyagent.repo.UserRepo;
import com.oziggyagent.oziggyagent.service.exception.UserDoesNotExist;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class UserDetailsServiceImp  implements UserDetailsService {
    private final UserRepo userRepo;





    @Override

    public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {
        User user=userRepo.findUserByEmailAddress(emailAddress);
        if (user==null){
            try {
                throw new UserDoesNotExist("user not found", HttpStatus.NOT_FOUND);
            } catch (UserDoesNotExist e) {
                throw new RuntimeException(e);
            }
        }

        List<GrantedAuthority> authorities = user.getAppRole().stream()
                .map(myRoles -> new SimpleGrantedAuthority("ROLE_".concat(myRoles.getTitle())))
                .collect(Collectors.toList());


        return new UserDetailsImpl(user.getId(),
                user.getPassword(),
                authorities,
                user.getEmailAddress());
    }
}

package com.oziggyagent.oziggyagent.serviceImp;

import com.oziggyagent.oziggyagent.dto.requestdto.AuthenticationRequest;
import com.oziggyagent.oziggyagent.dto.requestdto.OtpRequest;
import com.oziggyagent.oziggyagent.dto.requestdto.SuperAdminRegisterRequest;
import com.oziggyagent.oziggyagent.dto.requestdto.responesedto.AuthenticationResponse;
import com.oziggyagent.oziggyagent.model.*;
import com.oziggyagent.oziggyagent.repo.*;
import com.oziggyagent.oziggyagent.security.config.JwtService;
import com.oziggyagent.oziggyagent.service.SuperAdminService;
import com.oziggyagent.oziggyagent.service.exception.*;
import com.oziggyagent.oziggyagent.util.mapper.SuperAdminMapper;
import com.oziggyagent.oziggyagent.util.mapper.otpGenerator.OtpGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Component


public class SuperAdminImp implements SuperAdminService {
    private SuperAdminMapper superAdminMapper;

    private final AuthenticationManager authManager;

    @Autowired
    private OtpValidationRepo otpValidationRepo;

    @Autowired
    private final PasswordEncoder encoder;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private SuperAdminRepo superAdminRepo;
    @Autowired
    private MessageRepo messageRepo;

    private final OtpGenerator otpGenerate;
    private final JwtService jwtService;
    @Autowired
    private AgentRepo agentRepo;
    @Autowired
    private UserRepo userRepo;


    @Override
    public AuthenticationResponse register(SuperAdminRegisterRequest registerRequest) throws SuperAdminDoesNotExist, SuperAdminAlreadyExist {
        SuperAdmin superAdmin = superAdminRepo.findByEmailAddress(registerRequest.getEmailAddress());
       // SuperAdmin superAdmin=new SuperAdmin();

        if(superAdmin!=null){
            throw new SuperAdminAlreadyExist("superAdmin already exist", HttpStatus.BAD_REQUEST);

        }
        SuperAdmin mappedAdmin = superAdminMapper.map(registerRequest);

        Optional<MyRoles>supAdminRole= roleRepo.findByTitle(AppRole.SUPER_ADMIN.getValue());
        if (supAdminRole.isEmpty()){
            throw new SuperAdminDoesNotExist("role does not exist",HttpStatus.BAD_REQUEST);
        }
        mappedAdmin.addSuperAdmin(supAdminRole.get());

        String otpGen=otpGenerate.otpGenerator();
        OtpValidation validation=new OtpValidation();
        validation.setSuperAdmin(mappedAdmin);
        validation.setToken(otpGen);
        validation.setCreatedAt(LocalDateTime.now());
        validation.setExpireAt(LocalDateTime.now().plusMinutes(30));
        validation.setConfirmAt(null);

        superAdminRepo.save(mappedAdmin);
        otpValidationRepo.save(validation);


        return AuthenticationResponse.builder()
        .token(otpGen)
        .build();
    }



    @Override
    public String confirmation(OtpRequest otpRequest) throws SuperAdminDoesNotExist, AccountHasNotBeenConfirmed {

        OtpValidation tokenValidation = otpValidationRepo.findByToken(otpRequest.getToken());

        if (tokenValidation == null){
            throw new SuperAdminDoesNotExist("unknown  token validation",HttpStatus.BAD_REQUEST);
        }

        SuperAdmin superAdmin = tokenValidation.getSuperAdmin();
        LocalDateTime expiredTime = tokenValidation.getExpireAt();
        LocalDateTime confirmed = tokenValidation.getConfirmAt();
        if (confirmed != null) {
            throw new AccountHasNotBeenConfirmed("Account has already be confirmed",HttpStatus.NOT_ACCEPTABLE);
        }
        boolean isExpired = expiredTime.isBefore(LocalDateTime.now());

        if(isExpired) {
            superAdmin.setAppRole(null);
            superAdminRepo.deleteById(superAdmin.getId());
            tokenValidation.setSuperAdmin(null);
            superAdminRepo.delete(superAdmin);
            otpValidationRepo.delete(tokenValidation);
            return "expired token";
        }

        superAdmin.setEnable(true);
        tokenValidation.setConfirmAt(LocalDateTime.now());
        superAdminRepo.save(superAdmin);
        otpValidationRepo.save(tokenValidation);

      return "superAdmin account confirmed";
    }

    @Override
    public MyRoles superAdminMakeUserAdmin(long id) throws RoleDoesNotExist, UserDoesNotExist {

        User user=userRepo.findAUserById(id);
        if(user==null){
            throw new UserDoesNotExist("user doesnt exist",HttpStatus.NOT_FOUND);
        }

        Optional<MyRoles> adminRole=roleRepo.findByTitle(AppRole.ADMIN.getValue());
        if (adminRole.isEmpty()){
            throw new RoleDoesNotExist("adminRole you are trying to access doesnt exist",HttpStatus.NOT_FOUND);

        }

        user.addAdminRole(adminRole.get());



        return adminRole.get();
    }



    @Override
    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) throws SuperAdminDoesNotExist {

        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getEmailAddress(),
                        authenticationRequest.getPassword())
        );

        var jwtToken=jwtService.generateToken(authenticationRequest.getEmailAddress());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public SuperAdmin findSuperAdmin(SuperAdminRegisterRequest superAdminRegisterRequest) throws SuperAdminDoesNotExist {
        SuperAdmin findSuperAdmin=superAdminRepo.findByEmailAddress(superAdminRegisterRequest.getEmailAddress());


        if(findSuperAdmin==null){
            throw new SuperAdminDoesNotExist("superAdmin does not exist",HttpStatus.NOT_FOUND);
        }
        superAdminRepo.findByEmailAddress(superAdminRegisterRequest.getEmailAddress());

        return findSuperAdmin;
    }

    @Override
    public String delete(long id) throws SuperAdminDoesNotExist {

        SuperAdmin deleteSuperAdmin=superAdminRepo.findById(id);
        if(deleteSuperAdmin==null) {
            throw new SuperAdminDoesNotExist("superAdmin you are trying to delete does not exist", HttpStatus.NOT_FOUND);
        }
        OtpValidation superAdminValidation=otpValidationRepo.findBySuperAdmin(deleteSuperAdmin);
        superAdminValidation.setSuperAdmin(null);
        deleteSuperAdmin.setAppRole(null);
        superAdminRepo.delete(deleteSuperAdmin);

        return "successFully deleted";
    }




    @Override
    public String deleteUser(long id) throws UserDoesNotExist {
        User deleteUser=userRepo.findAUserById(id);
        if(deleteUser==null){
            throw new UserDoesNotExist("user doesnt exist",HttpStatus.NOT_FOUND);


        }

        OtpValidation userValidation=otpValidationRepo.findByUser(deleteUser);
        userValidation.setUser(null);
        deleteUser.setAppRole(null);
        deleteUser.setEnable(false);












        userRepo.delete(deleteUser);
        return "user successFully deleted";
    }



    @Override
    public String deleteAgent(long id) throws AgentDoesNotExist {
        Agent deleteAgent=agentRepo.findAgentById(id);
        if(deleteAgent==null) {
            throw new AgentDoesNotExist("agent doesNot exist", HttpStatus.NOT_FOUND);
        }
        OtpValidation agentValidation=new OtpValidation();
        agentValidation.setAgent(null);
        deleteAgent.setAgentLocations(null);
        deleteAgent.setAppRole(null);
        agentRepo.delete(deleteAgent);


        return "agent successfully deleted";
    }


}



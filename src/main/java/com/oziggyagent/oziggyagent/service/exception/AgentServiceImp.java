package com.oziggyagent.oziggyagent.service.exception;

import com.oziggyagent.oziggyagent.dto.AgentRegisterRequest;
import com.oziggyagent.oziggyagent.dto.MessageRequest;
import com.oziggyagent.oziggyagent.dto.requestdto.AuthenticationRequest;
import com.oziggyagent.oziggyagent.dto.requestdto.OtpRequest;
import com.oziggyagent.oziggyagent.dto.requestdto.responesedto.AuthenticationResponse;
import com.oziggyagent.oziggyagent.model.*;
import com.oziggyagent.oziggyagent.repo.*;
import com.oziggyagent.oziggyagent.security.config.JwtService;
import com.oziggyagent.oziggyagent.service.AgentService;
import com.oziggyagent.oziggyagent.util.mapper.AgentMapper;
import com.oziggyagent.oziggyagent.util.mapper.otpGenerator.OtpGenerator;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service

public class AgentServiceImp implements AgentService {
    @Autowired
    private AgentRepo agentRepo;
    @Autowired
    private UserRepo userRepo;

    private AuthenticationManager authManager;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private MessageRepo messageRepo;
    private final AgentMapper agentMapper;
    private final OtpGenerator otpGenerator;
    @Autowired
    private OtpValidationRepo otpValidationRepo;
    private JwtService jwtService;
    @Autowired
    private LocationRepo locationRepo;
   // private LocationRequest locationRequest;

    public AgentServiceImp(AgentMapper agentMapper, OtpGenerator otpGenerator) {
        this.agentMapper = agentMapper;
        this.otpGenerator = otpGenerator;
    }


    @Override
    public AuthenticationResponse registerAgent(AgentRegisterRequest agentRegisterRequest) throws AgentAlreadyExist, SuperAdminDoesNotExist, LocationDoesNotExist, LocationAlreadyExist {
        Agent registerAgent=agentRepo.findAgentByEmailAddress(agentRegisterRequest.getEmailAddress());
        if (registerAgent!=null){
            throw new AgentAlreadyExist("Agent Already Exist", HttpStatus.ALREADY_REPORTED);
        }
        Agent mapperAgent=agentMapper.map(agentRegisterRequest);


        Optional<MyRoles>agentRole=roleRepo.findByTitle(AppRole.AGENT.getValue());
        if (agentRole.isEmpty()){
            throw new SuperAdminDoesNotExist("agent does not exist",HttpStatus.NOT_FOUND);
        }
        mapperAgent.setAppRole(agentRole.get());
        MyLocation agentLocation = locationRepo.findByState(agentRegisterRequest.getLocation());
        if (agentLocation == null){
            throw new LocationDoesNotExist("invalid location",HttpStatus.ALREADY_REPORTED);

        }
        mapperAgent.getAgentLocations().add(agentLocation);



        String otpGen=otpGenerator.otpGenerator();
        OtpValidation validation=new OtpValidation();
        validation.setAgent(mapperAgent);
        validation.setToken(otpGen);
        validation.setExpireAt(LocalDateTime.now().plusMinutes(30));
        validation.setConfirmAt(LocalDateTime.now());

         agentRepo.save(mapperAgent);
         otpValidationRepo.save(validation);



        return AuthenticationResponse.builder()
                .token(otpGen)
                .build();

    }
@Transactional
    @Override
    public String confirm(OtpRequest otpRequest) throws AgentDoesNotExist, AccountHasNotBeenConfirmed {

        OtpValidation tokenValidation=otpValidationRepo.findByToken(otpRequest.getToken());

        if (tokenValidation==null){
            throw new AgentDoesNotExist("invalid otp",HttpStatus.BAD_REQUEST);
        }
        Agent agent=tokenValidation.getAgent();

        LocalDateTime expiredTime=tokenValidation.getExpireAt();

        LocalDateTime confirmTime=tokenValidation.getConfirmAt();
        if (confirmTime==null){
            throw new AccountHasNotBeenConfirmed("Account has not been confirm", HttpStatus.ACCEPTED);
        }

        boolean isExpired=expiredTime.isBefore(LocalDateTime.now());
        if (isExpired){

            agent.setAppRole(null);

            agent.setAgentLocations(null);

            agentRepo.deleteById(agent.getId());

            tokenValidation.setAgent(null);

            agentRepo.delete(agent);

          return "expired token" ;

        }

        agent.setEnable(true);

        tokenValidation.setConfirmAt(LocalDateTime.now());

        agentRepo.save(agent);

        return "Account confirmed welcome";






    }

    @Override
    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {

     authManager.authenticate(
             new UsernamePasswordAuthenticationToken(authenticationRequest.getEmailAddress(),
                     authenticationRequest.getPassword())


     )  ;

        var  jwtToken=jwtService.generateToken(authenticationRequest.getEmailAddress());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public Agent findAgent(String emailAddress) throws AgentDoesNotExist {

        Agent findAgent =agentRepo.findAgentByEmailAddress(emailAddress);
        if (findAgent==null){
            throw new AgentDoesNotExist("the agent you are searching for does not exist",HttpStatus.NOT_FOUND);
        }
        agentRepo.findAgentByEmailAddress(findAgent.getEmailAddress());
        return findAgent;
    }




    @Override
    public ResponseEntity<?> searchLocation(long userId, MyLocation location) throws NoAgentAssignToThisLocation, UserDoesNotExist {
        User user = userRepo.findAUserById(userId);
        if(user==null){
            throw new UserDoesNotExist("user does not exist", HttpStatus.NOT_FOUND);
        }

        List<Agent> myAgent = agentRepo.findByAgentLocations(location);
        if (myAgent.isEmpty()){
            throw new NoAgentAssignToThisLocation("no agent Assign to this location",HttpStatus.NOT_FOUND);
        }
       // Agent agent = myAgent.get(0);
        myAgent.get(0).assignUser(user);
        return ResponseEntity.ok("Agent assigned to user");
    }

    @Override
    public ResponseEntity<?> messageUser(long userId, long agentId, MessageRequest messageRequest) throws UserDoesNotExist {
        User user = userRepo.findAUserById(userId);
        if(user==null){
            throw new UserDoesNotExist("user does not exist", HttpStatus.NOT_FOUND);
        }

        Agent agent = agentRepo.findAgentById(agentId);
        if(agent==null){
            throw new UserDoesNotExist("Agent does not exist", HttpStatus.NOT_FOUND);
        }
        Message message = new Message();
        message.setAgent(agent);
        message.setContent(messageRequest.getContent());
        message.setTime(LocalDateTime.now());

        messageRepo.save(message);
        user.addMessage(message);
        userRepo.save(user);

        return ResponseEntity.ok("message sent");
    }
}

package com.oziggyagent.oziggyagent.serviceImp;

import com.oziggyagent.oziggyagent.dto.AgentRegisterRequest;
import com.oziggyagent.oziggyagent.dto.MessageRequest;
import com.oziggyagent.oziggyagent.dto.requestdto.*;
import com.oziggyagent.oziggyagent.dto.requestdto.responesedto.AuthenticationResponse;
import com.oziggyagent.oziggyagent.model.*;
import com.oziggyagent.oziggyagent.repo.*;
import com.oziggyagent.oziggyagent.security.config.JwtService;
import com.oziggyagent.oziggyagent.service.UserService;
import com.oziggyagent.oziggyagent.service.exception.*;
import com.oziggyagent.oziggyagent.util.mapper.UserMapper;
import com.oziggyagent.oziggyagent.util.mapper.otpGenerator.OtpGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor

@Service


public class UserServiceImp implements UserService {
    @Autowired
    private final UserRepo userRepo;
    @Autowired
    private AgentRepo agentRepo;

    private final UserMapper userMapper;
    @Autowired
    private LocationRepo locationRepo;

    private MyLocation myLocation;
    @Autowired
    private RoleRepo roleRepo;
    private final OtpGenerator otpGenerator;

    @Autowired
    private OtpValidationRepo validationRepo;

    @Autowired
    private AuthenticationManager authManager;
    @Autowired

    private JwtService jwtService;
    @Autowired
    private MessageRepo messageRepo;
    private AgentRegisterRequest agentRequest;


    @Override
    public AuthenticationResponse register(UserRegisterRequest registerRequest) throws UserAlreadyExist, RoleDoesNotExist {

        User registerUser = userRepo.findUserByEmailAddress(registerRequest.getEmailAddress());
        if (registerUser != null) {
            throw new UserAlreadyExist("user already exist", HttpStatus.ALREADY_REPORTED);
        }

        User myUser = userMapper.map(registerRequest);
        Optional<MyRoles> userRole = roleRepo.findByTitle(AppRole.USER.getValue());
        if (userRole.isEmpty()) {
            throw new RoleDoesNotExist("role does not exist",HttpStatus.NOT_FOUND);
        }
        myUser.addUserRole(userRole.get());



        String otpGen = otpGenerator.otpGenerator();
        OtpValidation validation = new OtpValidation();
        validation.setUser(myUser);
        validation.setToken(otpGen);
        validation.setExpireAt(LocalDateTime.now().plusMinutes(60));
        validation.setConfirmAt(LocalDateTime.now());

        userRepo.save(myUser);
        validationRepo.save(validation);


        return AuthenticationResponse.builder()
                .token(otpGen)
                .build();
    }



    @Transactional
    @Override
    public String confirmation (OtpRequest otpRequest) throws TokenDoesNotExist, UserDoesNotExist {

        OtpValidation tokenValidate = validationRepo.findByToken(otpRequest.getToken());
        if (tokenValidate == null) {
            throw new TokenDoesNotExist("invalid token validation", HttpStatus.NOT_FOUND);
        }
        User user = tokenValidate.getUser();

        LocalDateTime expiredAt = tokenValidate.getExpireAt();
        LocalDateTime confirmedAt = tokenValidate.getConfirmAt();
        if (confirmedAt == null) {
            throw new UserDoesNotExist("account Not Confirmed Yet", HttpStatus.ALREADY_REPORTED);
        }

        boolean isExpired = expiredAt.isBefore(LocalDateTime.now());
        if (isExpired) {
            user.setAppRole(null);
            userRepo.deleteById(user.getId());
            tokenValidate.setUser(null);
            userRepo.delete(user);
            validationRepo.delete(tokenValidate);

            return "expired token";

        }

        user.setEnable(true);
        tokenValidate.setConfirmAt(LocalDateTime.now());
        userRepo.save(user);

        return "account confirmed";
    }








    @Override
    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getEmailAddress(),
                authenticationRequest.getPassword()
        ));
        var jwtToken = jwtService.generateToken(authenticationRequest.getEmailAddress());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public User findUser(UserFindRequest userFindRequest) throws UserDoesNotExist {
        User findUser = userRepo.findUserByEmailAddress(userFindRequest.getEmailAddress());
        if (findUser == null) {
            throw new UserDoesNotExist("user does not exist", HttpStatus.NOT_FOUND);


        }
        userRepo.findUserByEmailAddress(findUser.getEmailAddress());

        return  findUser;

    }








    @Override
        public Map<String, Object> searchLocation (LocationRequest locationRequest, long id) throws
            AgentLocationDoesNotExist, LocationDoesNotExist, UserDoesNotExist, AgentDoesNotExist {
            User user = userRepo.findAUserById(id);
            if (user == null) {
                throw new UserDoesNotExist("user does not exist", HttpStatus.NOT_FOUND);
            }

        MyLocation location = locationRepo.findByState(locationRequest.getState());
            if (location == null) {
                    throw new LocationDoesNotExist("unKnow  location", HttpStatus.NOT_FOUND);
            }

            List<Agent> agents = agentRepo.findByAgentLocations(location);

            if (agents.isEmpty()) {
                throw new AgentLocationDoesNotExist("no agent assign to this location", HttpStatus.NOT_FOUND);
            }

            user.assignAgent(agents.get(0));

            String locationInfo=fetchLocation(location.getState(),agents);


        Map<String,Object>agentDetails=new HashMap<>();
        agentDetails.put("name", agents.get(0).getFirstname());
        agentDetails.put("phoneNumber", agents.get(0).getPhoneNumber());
        agentDetails.put("locationInfo",locationInfo);


            System.out.println("Agent " + agents.get(0).getFirstname() + " is now assigned to you ");
            return agentDetails;
        }


        public String fetchLocation (String location, List < Agent > agent) throws AgentDoesNotExist {

        if (location==null||agent==null||agent.isEmpty()){
            throw new AgentDoesNotExist("the agent you enter does not exist",HttpStatus.NOT_FOUND);
        }
        StringBuilder userInfo=new StringBuilder();
            for (int i=0;i<agent.size();i++) {
                Agent agent1=new Agent();
                userInfo.append(agent1.getFirstname());
               userInfo.append("(");
               userInfo.append(agent1.getPhoneNumber());
               userInfo.append(")");


            }




        return location + " for " + userInfo.toString();
        }
    @Override
    public ResponseEntity<?> sendMessage(long agentId, long userId, MessageRequest messageRequest) throws AgentDoesNotExist, UserDoesNotExist {
        User user=userRepo.findAUserById(userId);
        if (user==null){
            throw new UserDoesNotExist("user doesnt exist",HttpStatus.NOT_FOUND);
        }

        Agent messageAgent=agentRepo.findAgentById(agentId);
        if (messageAgent==null){
            throw new AgentDoesNotExist("agent doesnt exist",HttpStatus.NOT_FOUND);
        }

        Message sendMessage=new Message();
        sendMessage.setUser(user);
        sendMessage.setContent(messageRequest.getContent());
        sendMessage.setTime(LocalDateTime.now());
        sendMessage.setAgent(messageAgent);

        messageRepo.save(sendMessage);
        messageAgent.addMessage(sendMessage);
        agentRepo.save(messageAgent);



        return ResponseEntity.ok("message sent");








    }









    }

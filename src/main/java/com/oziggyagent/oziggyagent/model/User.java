package com.oziggyagent.oziggyagent.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;

import javax.validation.constraints.NotBlank;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private boolean enable=false;
    
    private String password;


    private String firstName;
    private String lastName;

    private String phoneNumber;

    private String emailAddress;
    private String location;
      @ManyToMany
    private List<Agent> agents=new ArrayList<>();





    @OneToMany
    private Set<Message> myMessages = new HashSet<>();

    @ManyToMany( fetch = FetchType.EAGER)
    private Set <MyRoles> appRole = new HashSet<>();


    public void addUserRole(MyRoles role){
        this.appRole.add(role);
    }

    public void addAdminRole(MyRoles roles){
        this.appRole.add(roles);
    }

    public  void addMessage(Message message){
        this.myMessages.add(message);

    }

    public  void assignAgent(Agent agent){
        this.agents.add(agent);

    }


}

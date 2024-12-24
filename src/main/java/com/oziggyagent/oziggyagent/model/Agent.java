package com.oziggyagent.oziggyagent.model;

import jakarta.persistence.*;
import lombok.*;
import org.intellij.lang.annotations.JdkConstants;
import org.intellij.lang.annotations.Pattern;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Agent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private  boolean isEnable=false ;

    private String firstname;

    private String lastname;
//@Pattern(regexp="^[\\w.-]+@[a-zA-Z\\d.-]+\\.[a-zA-Z]{2,}$")
    private  String emailAddress;

    private String phoneNumber;
   // @Pattern(regexp="^((?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$&*])(?=.*[0-9])){4,12}$")
    private String password;
    @OneToMany
    private List<MyLocation> agentLocations=new ArrayList<>();


    @ManyToMany
    private List<User>users=new ArrayList<>();

    @ManyToOne
    private MyRoles appRole;
    @OneToMany
    private Set<Message> myMessage=new HashSet<>();

    public  void assignUser(User user){
        this.users.add(user);
    }

    public void addMessage(Message sendMessage) {
        this.myMessage.add(sendMessage);
    }


}

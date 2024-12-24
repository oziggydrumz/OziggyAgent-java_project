package com.oziggyagent.oziggyagent.util.mapper.otpGenerator;

import org.springframework.stereotype.Component;

@Component

public class OtpGenerator {
    private int otpLength=4;

    public String otpGenerator(){
        int[]otpGenerator=new int[otpLength];
        for(int count=0;count<otpGenerator.length;count++){
            otpGenerator[count]=(int)(Math.random()*9);
        }
String myOtp=""+ otpGenerator[0]+otpGenerator[1]+otpGenerator[2]+otpGenerator[3];
        return myOtp;
    }



}

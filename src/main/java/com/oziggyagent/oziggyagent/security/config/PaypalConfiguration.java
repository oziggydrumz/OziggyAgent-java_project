package com.oziggyagent.oziggyagent.security.config;

import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class PaypalConfiguration {
    @Value("$(paypal.clint.id)")
    private String clintId;

    @Value("$(paypal.clint.secret")
    private String clintSecret;

    @Value("$(paypal.clint.mode)")
    private String mode;
   @Bean
   public Map<String,String>paypalSdkConfig(){
       Map<String,String>configMap=new HashMap<>();
       configMap.put("mode",mode);
       return configMap;


   }
   @Bean
   public OAuthTokenCredential oauthTokenCredential(){
    return new OAuthTokenCredential(clintId,clintSecret,paypalSdkConfig());
   }
   @Bean
   public APIContext apiContext() throws PayPalRESTException {
APIContext context=new APIContext(oauthTokenCredential().getAuthorizationHeader());
   context.setConfigurationMap(paypalSdkConfig());
    return context;
   }



}

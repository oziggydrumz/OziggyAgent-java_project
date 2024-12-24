package com.oziggyagent.oziggyagent.security.config;

import com.oziggyagent.oziggyagent.util.mapper.KeyConfig;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtService {

    private final KeyConfig keyConfig;

    public JwtService(KeyConfig keyConfig) {
        this.keyConfig = keyConfig;
    }


    public String extractEmailAddress(String token) {
        return extractClaim(token);

    }

public String extractClaim(String token){
        return extractAllClaims(token);

}



public String generateToken(String emailAddress) {

        return createToken(emailAddress);


}

    private String createToken(String emailAddress) {
        return Jwts.builder()
                .setSubject(emailAddress)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()*1000*60*60*24))
                .signWith(getSignInKey(),SignatureAlgorithm.HS256)
               .compact();
    }

    private Key getSignInKey() {

        return Keys.hmacShaKeyFor(keyConfig.getSecret().getBytes(StandardCharsets.UTF_8));

    }

    public boolean isTokenValid(String token,UserDetails userDetails){
        try{
            String emailAddress=extractEmailAddress(token);
            return (emailAddress.equals(userDetails.getUsername())&& !isTokenExpired(token));

        } catch (JwtException |IllegalArgumentException e){
            throw new IllegalArgumentException("invalid or expire token");

        }


}

    private boolean isTokenExpired(String token) {
        Claims claims=Jwts.parserBuilder()
                .setSigningKey(getSignInKey()).build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getExpiration().before(new Date());
    }



    private String extractAllClaims(String token){
      return Jwts
              .parserBuilder()
              .setSigningKey(getSignInKey())
              .build()
              .parseClaimsJws(token)
              .getBody()
              .getSubject();

    }


}


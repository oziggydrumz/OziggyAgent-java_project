package com.oziggyagent.oziggyagent.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class JwtService {
    private static final String SECRETE_KEY="tlvwKt2GUj4UAAySbR3cRnzCfRNteztTuhnVA6u+iD0RYmLztsD/HST6ALRA2Txcn40qydOhb+rK2k3MXD0avVV1741MANsM8rpdAkyklUw+Dzk4sqYMD+IZA1McEP6joTkD6ifPIy+Q9KWDidRWfFbUQlS253bWzBa02eIqQn+AdtKbWnkbcwo4//+k1IWiaPHmthFs979uIPPqf1B4CvmDmP5JKgXrhMqSqfFBZWz9wJ6nuinxBDd1VdbkpaY+gZLuaZ2MK/Bm3jsJ/OkHFfqMAQW/r34osA6MfSsMxZMB1M5vajPdTu3Go5AYVydE9xxnQwMqgggoNQLTHZn+3w==\n";



    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    public <T> T extractClaim(String token, Function<Claims,T> claimResolver){
        final Claims claims=extractAllClaims(token);
        return claimResolver.apply(claims);


    }
    public String generateToken(UserDetails userDetails){
        return  generateToken(new HashMap<>(),userDetails);
    }
    public String generateToken(
            Map<String, Objects> extraClaims,
            UserDetails userDetails
    ){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();

    }
    public boolean isTokenValid(String token, UserDetails userDetails){
        final  String username=extractEmail(token);
        return(username.equals(userDetails.getUsername()))&& !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }

    private Key getSignInKey() {
        byte[]keyBytes= Decoders.BASE64.decode(SECRETE_KEY);
        return Keys.hmacShaKeyFor(keyBytes);

    }
}

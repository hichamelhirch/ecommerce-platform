package org.sid.ecommerce.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    public static final String SECRET="6d6b1c2e8a9f3d47e5c4a2f6b1d9e3c6f4a7b5c8e6f2a1d3b9e4c8f5d6a7b9e0d";


    public String generateToken(String userName){
        Map<String,Object> claims=new HashMap<>();
        return createToken(claims,userName);
    }

    private String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    private Key getSignKey(){
        byte[] keybytes= Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keybytes);
    }

    public String extractUsername(String token){
        return  extractClaim(token,Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
      final  Claims claims=extractAllClaim(token);
      
      return claimsResolver.apply(claims);
    }

    private Claims extractAllClaim(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
    }

    private Boolean isTokeExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }

  public Boolean validateToken(String token, UserDetails userDetails){
        final String username=extractUsername(token);
        return (username.equals(userDetails.getUsername()) &&!isTokeExpired(token));
  }
}

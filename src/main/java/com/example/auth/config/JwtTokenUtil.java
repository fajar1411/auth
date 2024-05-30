package com.example.auth.config;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = -2550185165626007488L;
    public static final long JWT_TOKEN_VALIDITY = 1 * 60 * 60;
    public static final long JWT_TOKEN_Refresh = 1 * 60 * 60;

    @Value("${jwt.secret}")
    private String secret;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("admin"))) {
            claims.put("role", "admin");
        } else if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("manager"))) {
            claims.put("role", "manager");
        } else {
            claims.put("role", "member");
        }
        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

  

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String refreshToken(String token, UserDetails userDetails) {
        
        if (isTokenExpired(token)) {
            final Claims claims = getAllClaimsFromToken(token);
            if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("admin"))) {
				claims.put("role", "admin");
			} else if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("manager"))) {
				claims.put("role", "hr");
			} else {
				claims.put("role", "member");
			}
            Date expirationDate = claims.getExpiration();
            claims.setIssuedAt(new Date(System.currentTimeMillis()));
            claims.setExpiration(new Date(expirationDate.getTime() + JWT_TOKEN_Refresh * 1000));
            return doGenerateToken(claims, getUsernameFromToken(token));
        }
        return token;
    }
}
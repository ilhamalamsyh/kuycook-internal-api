package com.kuycook.kuycookinternalapi.security.jwt;

import com.kuycook.kuycookinternalapi.models.Employee;
import com.kuycook.kuycookinternalapi.security.services.EmployeeDetailsImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${kuycook.api.jwtSecret}")
    private String jwtSecret;

    @Value("${kuycook.api.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {
        EmployeeDetailsImpl employeePrincipal = (EmployeeDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((employeePrincipal.getEmail()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date().getTime() + jwtExpirationMs)))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getEmailFromJwtToken(String token) {
        System.out.println("getEmailFromJwtToken: " + Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject());
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid Jwt signature : {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid jwt token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("Jwt token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("Jwt token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("Jwt claims string empty: {}", e.getMessage());
        }
        return false;
    }


}

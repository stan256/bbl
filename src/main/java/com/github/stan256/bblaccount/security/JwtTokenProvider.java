package com.github.stan256.bblaccount.security;

import com.github.stan256.bblaccount.model.CustomUserDetails;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Slf4j
@Component
public class JwtTokenProvider {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration}")
    private Long jwtExpirationInMs;

    public String generateAccessToken(CustomUserDetails customUserDetails) {
        return generateAccessTokenFromUserId(customUserDetails.getId());
    }

    public String generateAccessTokenFromUserId(Long userId) {
        Instant expiryDate = Instant.now().plusMillis(jwtExpirationInMs);
        return Jwts.builder()
                .setSubject(Long.toString(userId))
                .setHeader((Map<String, Object>) Jwts.header().setType("JWT"))
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(expiryDate))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }


    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
            throw new RuntimeException("Incorrect signature: " + authToken);
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
            throw new RuntimeException("Malformed jwt token: " + authToken);
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
            throw new RuntimeException("Token expired. Refresh required: " + authToken);
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
            throw new RuntimeException("Unsupported JWT token: " + authToken);
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
            throw new RuntimeException("Illegal argument token: " + authToken);
        }
    }

    public Long getExpiryDuration() {
        return jwtExpirationInMs;
    }
}

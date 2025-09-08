package com.ajayaraj.task_manager.utilis;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET = "MAHESHBa123qwerty34567dfghjFGHJ45678JHGAMAXINGSPIDER";
    private final long EXPIRATION = 1000 * 60 * 60;

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateJwtToken(String userName) {
        return Jwts
                .builder()
                .subject(userName)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getSignInKey(), Jwts.SIG.HS256)
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUserName(String token) {
        return extractClaims(token)
                .getSubject();
    }

    public boolean validateToken(String token, String userName) {
        String extractedUserName = extractUserName(token);
        return !isTokenExpired(token) && (extractedUserName.equals(userName));
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token)
                .getExpiration().before(new Date());
    }

}

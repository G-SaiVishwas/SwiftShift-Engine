package org.example.rideshare.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for JWT token operations.
 * Handles token generation, validation, and parsing.
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    // Get the signing key from our secret
    private SecretKey getSigningKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Generate a new JWT token for the given user.
     * Token includes username, role, and user id as claims.
     */
    public String generateToken(String username, String role, String userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        claims.put("userId", userId);

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Extract all claims from a token.
     * Throws exception if token is invalid or expired.
     */
    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Get username from token
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Get role from token
    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    // Get user id from token
    public String extractUserId(String token) {
        return extractAllClaims(token).get("userId", String.class);
    }

    // Check if token is expired
    public boolean isTokenExpired(String token) {
        Date expiry = extractAllClaims(token).getExpiration();
        return expiry.before(new Date());
    }

    /**
     * Validate token against a username.
     * Token is valid if username matches and token hasn't expired.
     */
    public boolean validateToken(String token, String username) {
        try {
            String tokenUsername = extractUsername(token);
            return tokenUsername.equals(username) && !isTokenExpired(token);
        } catch (Exception e) {
            // Token parsing failed - invalid token
            return false;
        }
    }
}

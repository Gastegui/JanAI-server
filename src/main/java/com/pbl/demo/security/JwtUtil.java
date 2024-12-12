package com.pbl.demo.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    private final Key key;
    private final long expire;

    public JwtUtil(@Value("${jwt.secret}") String secret, @Value("${jwt.expiration}") long expire) {
        this.expire = expire;
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * @brief Get all claims included inside a given JWT token
     * @return List of all claims from provided token
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * @brief Extracts any given claim of type "T" from the provided token
     * @return The information resolved within the token for claim of type "T"
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * @brief This method generates JWT tokens for session authentication
     * and validation within API.
     * @return JWT token encrypted and with all information necessary (
     * Username, Expiration, Sign, Claims
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList()); // Add roles to claims

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expire))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * @brief Method to extract username from given token
     * @return Username provided in token
     */
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * @brief Method to call all helper methods to validate fully whether
     * a token provided is valid
     * @return Boolean value of token if valid, or not
     */
    public boolean validateToken(String token, String username) {
        String extractedUsername = extractUsername(token);
        return (username.equals(extractedUsername) && !isTokenExpired(token));
    }

    /**
     * @brief This method validates whether the token provided is
     * expired
     * @return Boolean value of token expiration time
     */
    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }

    /**
     * @brief This method extracts the expiration time from a given token
     * @return The expiration time from token
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * @brief This method extracts roles from a given token
     * @return List of roles provided in token
     */
    public List<String> extractRoles(String token) {
        Claims claims = extractAllClaims(token);
        return (List<String>) claims.get("roles");
    }
}

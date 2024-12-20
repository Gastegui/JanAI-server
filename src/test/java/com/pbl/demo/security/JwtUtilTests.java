package com.pbl.demo.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.ExpiredJwtException;

class JwtUtilTests {    

    private JwtUtil jwtUtil;
    private UserDetails userDetails;
    private String token;

    @BeforeEach
    void setUp() {
        //Utilities for token generation
        jwtUtil = new JwtUtil("an-incredibly-safe-testing-key-with-over-256-bits", 36000);
        userDetails = new User("username", "password", List.of(new SimpleGrantedAuthority("ROLE_USER")));
        token = jwtUtil.generateToken(userDetails);
    }

    @Test
    void testGenerateToken() {
        assertNotNull(token, "Generated token should not be null.");
        
        // Extract claims from the generated token and verify them
        String username = jwtUtil.extractUsername(token);
        assert(username.equals("username"));

        // Verifying that roles are correctly included
        List<String> roles = jwtUtil.extractRoles(token);
        assert(roles.contains("ROLE_USER"));
    }

    @Test
    void testExtractClaimWithCustomClaimFunction() {
        List<String> roles = jwtUtil.extractClaim(token, claims -> (List<String>) claims.get("roles"));

        // Verify the custom claim is correctly extracted
        assertNotNull(roles, "Roles should not be null.");
        assert(roles.contains("ROLE_USER"));
    }

    @Test
    void testTokenValidation() throws InterruptedException {
        JwtUtil shortLivedJwtUtil = new JwtUtil("an-incredibly-safe-testing-key-with-over-256-bits", 1000);
        String shortLivedToken = shortLivedJwtUtil.generateToken(userDetails);
    
        // Validate token immediately
        assertTrue(shortLivedJwtUtil.validateToken(shortLivedToken, userDetails.getUsername()), "Token should be valid right after creation.");
        assertFalse(shortLivedJwtUtil.validateToken(shortLivedToken, "wrong__username"),"Token should be invalid if wrong username provided.");
    
        // Wait for the token to expire
        Thread.sleep(1500); // Sleep for 1.5 seconds (longer than 1-second expiration)
        // Validate token again, expecting it to be expired
        assertThrows(ExpiredJwtException.class, () -> {
            shortLivedJwtUtil.validateToken(shortLivedToken, userDetails.getUsername());
        }, "Token should be invalid after expiration.");

        assertThrows(ExpiredJwtException.class, () -> {
            shortLivedJwtUtil.validateToken(shortLivedToken, "wrong_username");
        }, "Token should be invalid after expiration.");
    }

    @Test
    void testValidationToken() {
        assertEquals(Date.class, jwtUtil.extractExpiration(token).getClass());
    }

}

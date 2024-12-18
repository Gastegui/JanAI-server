package com.pbl.demo.security;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

class JwtUtilTests {    

    private JwtUtil jwtUtil;
    private UserDetails userDetails;
    private String token;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil("an-incredibly-safe-testing-key-with-over-256-bits", 360000);
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
    void testClaimAccess() {
        ass
    }

    @Test
    void testValidationToken() {
        assertTrue(jwtUtil.validateToken(token, userDetails.getUsername()));
        assertFalse(jwtUtil.validateToken(token, "wrongName"));
    }

}

package com.pbl.demo.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // @Test
    // void testTokenExpiration() throws InterruptedException {
    //     Thread.sleep(3000); // Sleep longer than the expiration time

    //     // Validate that the token is expired
    //     assertThrows(ExpiredJwtException.class, () -> {
    //         jwtUtil.validateToken(token, userDetails.getUsername());
    //     }, "Token should expire after timeout."
    //     );
    // }

    @Test
    void testTokenNotExpired() {
        // Check that the token is still valid before expiration
        assertTrue(jwtUtil.validateToken(token, userDetails.getUsername()), "Token should not be expired yet.");
    }

    @Test
    void testValidationToken() {
        assertTrue(jwtUtil.validateToken(token, userDetails.getUsername()));
        assertFalse(jwtUtil.validateToken(token, "wrongName"));
    }

}

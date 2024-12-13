package com.pbl.demo.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    MyUserDetailsService userDetailsService;

    /**
     * @brief This is an internal filter to be called before any request
     * to validate whether or not the JWT token provided is valid and authenticated
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            try {
                String username = jwtUtil.extractUsername(token);
                List<String> roles = jwtUtil.extractRoles(token);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    System.out.println("USERNAME IS NOT NULL");
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    if (jwtUtil.validateToken(token, userDetails.getUsername())) {
                        System.out.println("TOKEN IS VALID");
                        List<SimpleGrantedAuthority> authorities = roles.stream()
                                .map(SimpleGrantedAuthority::new)
                                .toList();

                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                userDetails, null, authorities);
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            } catch (Exception e) {
                // Log error and reject token
                System.out.println("Invalid token: " + e.getMessage());
            }
        }

        chain.doFilter(request, response);
    }
}

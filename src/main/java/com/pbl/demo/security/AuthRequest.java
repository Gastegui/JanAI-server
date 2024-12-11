package com.pbl.demo.security;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String userpass;
}

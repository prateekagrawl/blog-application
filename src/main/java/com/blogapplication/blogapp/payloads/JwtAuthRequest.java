package com.blogapplication.blogapp.payloads;

import lombok.Data;

@Data
public class JwtAuthRequest {

    private String username;
    private String password;
}

package com.blog.Security;

import com.blog.entities.User;
import com.blog.payloads.UserDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JWTAuthResponse {
    private String token;
    private UserDto user;
}

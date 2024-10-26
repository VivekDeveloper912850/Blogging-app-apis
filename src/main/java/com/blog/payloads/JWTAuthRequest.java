package com.blog.payloads;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JWTAuthRequest {
    private String Email;
    private String Password;
}

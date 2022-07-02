package com.minejava.springjwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class TokenDTO {
    private String userId;

    private String accessToken;
    private String refreshToken;
}

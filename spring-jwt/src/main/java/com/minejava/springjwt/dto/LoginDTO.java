package com.minejava.springjwt.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginDTO {
    
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}

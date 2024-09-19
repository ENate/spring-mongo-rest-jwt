package com.minejava.springjwt.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignupDTO {

    @NotBlank
    @Size(min = 4, max = 60)
    private String username;

    @NotBlank
    @Size(min = 6, max = 60)
    private String email;
    @NotBlank
    @Size(min = 6, max = 50)
    private String password;
}

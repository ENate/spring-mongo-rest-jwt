package com.minejava.springjwt.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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

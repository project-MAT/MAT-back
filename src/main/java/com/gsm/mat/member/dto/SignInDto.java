package com.gsm.mat.member.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class SignInDto {
    @NotBlank(message = "Id should be valid")
    private String email;
    @NotBlank(message = "Password should be valid")
    private String password;
}

package com.gsm.mat.member.dto;

import com.gsm.mat.member.Member;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class MemberDto {
    @Email(message = "email should be valid")
    @NotBlank(message = "email should be valid")
    private String email;

    @NotBlank(message = "password should be valid")
    private String password;

    @NotBlank(message = "name should be valid")
    @Size(min = 3,max = 10)
    private String name;

    @NotBlank(message = "Cardinal Number should be valid")
    private int cardinalNum;

    @NotBlank(message = "Majority should be valid")
    private String majority;

    @NotBlank(message = "Discord Number should be valid")
    private String discordNum;
    
    public Member toEntity(){
        return Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .cardinalNum(cardinalNum)
                .discordNum(discordNum)
                .majority(majority)
                .build();
    }
}

package com.gsm.mat.tag.dto;

import com.gsm.mat.tag.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
@RequiredArgsConstructor
public class TagDto {
    @NotBlank(message = "Content should be valid")
    private String content;

    public Tag toEntity(){
        return new Tag(content);
    }
}

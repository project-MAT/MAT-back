package com.gsm.mat.notification.dto;

import com.gsm.mat.notification.Notification;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Builder
@Getter @Setter
@AllArgsConstructor
public class NotificationDto {
    @NotBlank(message = "title should be valid")
    private String title;

    @NotBlank(message = "content should be valid")
    private String content;

    public Notification toEntity(){
        return Notification.builder()
                .content(content)
                .title(title)
                .build();
    }
}

package com.gsm.mat.notification.service;

import com.gsm.mat.notification.Notification;
import com.gsm.mat.notification.dto.NotificationDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NotificationServiceTest {
    @Autowired
    NotificationService notificationService;
    @Autowired
    NotificationRepository notificationRepository;

    @Test
    public void join(){
        NotificationDto notificationDto =new NotificationDto("title","content");
        Long savedId = notificationService.create(notificationDto);
        Assertions.assertThat(notificationService.findOne(savedId).getTitle()).isEqualTo("title");
        Assertions.assertThat(notificationService.findOne(savedId).getContent()).isEqualTo("content");
    }
    @Test
    public void findOne(){
        NotificationDto notificationDto =new NotificationDto("title","content");
        notificationService.create(notificationDto);
        Notification one = notificationRepository.findById(1L);
        Assertions.assertThat(one.getTitle()).isEqualTo("title");
        Assertions.assertThat(one.getContent()).isEqualTo("content");
    }
    @Test
    public void addGood(){
        NotificationDto notificationDto =new NotificationDto("title","content");
        Long findId = notificationService.create(notificationDto);
        notificationService.addGoods(findId);
        Assertions.assertThat(notificationService.findOne(findId).getGoods()).isEqualTo(1);
    }
}
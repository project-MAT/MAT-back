package com.gsm.mat.tag.service;

import com.gsm.mat.notification.Notification;
import com.gsm.mat.notification.service.NotificationService;
import com.gsm.mat.tag.Tag;
import com.gsm.mat.tag.dto.TagDto;
import com.gsm.mat.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TagService {
    private final TagRepository tagRepository;
    private final NotificationService notificationService;
    public void create(List<TagDto> tagDto,Long notificationIdx){
        List<Tag> tags = new ArrayList<>();
        Notification notification = notificationService.findOne(notificationIdx);
        for (int i = 0; i < tagDto.size(); i++) {
            tags.add(tagDto.get(i).toEntity());
            Tag tag = tags.get(i);
            tag.setNotification(notification);
            tagRepository.save(tag);
        }
    }
    public List<Tag> findByNotification(Long notificationIdx){
        return tagRepository.findByNotification(notificationIdx);
    }
    public void deleteTag(Long tagIdx){
        tagRepository.delete(tagIdx);
    }
}

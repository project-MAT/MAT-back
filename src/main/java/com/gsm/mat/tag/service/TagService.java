package com.gsm.mat.tag.service;

import com.gsm.mat.notification.service.NotificationService;
import com.gsm.mat.tag.Tag;
import com.gsm.mat.tag.dto.TagDto;
import com.gsm.mat.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TagService {
    private final TagRepository tagRepository;
    private final NotificationService notificationService;
    public Long create(TagDto tagDto,Long notificationIdx){
        Tag tag = tagDto.toEntity();
        tag.setNotification(notificationService.findOne(notificationIdx));
        return tagRepository.save(tag);
    }
    public List<Tag> findByNotification(Long notificationIdx){
        return tagRepository.findByNotification(notificationIdx);
    }
    public void deleteTag(Long tagIdx){
        tagRepository.delete(tagIdx);
    }
}

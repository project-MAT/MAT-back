package com.gsm.mat.tag.repository;

import com.gsm.mat.notification.Notification;
import com.gsm.mat.tag.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    public List<Tag> findByNotification(Notification notification);
}

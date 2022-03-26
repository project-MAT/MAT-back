package com.gsm.mat.notification.repository;

import com.gsm.mat.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    public List<Notification> findAllByTitleLike(String keyWord);
    @Query("select n from Notification n order by n.goods desc ")
    List<Notification> findAllOrderByGoods();
    @Query("select p from Notification p order by p.notification_id desc ")
    public List<Notification> findAllOrderByNewer();
}

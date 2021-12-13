package com.gsm.mat.notification.repository;

import com.gsm.mat.notification.Notification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class NotificationRepository {
    @PersistenceContext
    EntityManager em;
    public Long save(Notification notification){
        em.persist(notification);
        return notification.getNotification_id();
    }
    public void update(Notification notification, Notification before){
        em.createQuery("update Notification m set m.title=:notificationTitle,m.content=:notificationContent where m.notification_id=:notificationIdx")
                .setParameter("notificationTitle", notification.getTitle())
                .setParameter("notificationContent", notification.getContent())
                .setParameter("notificationIdx",before.getNotification_id())
                .executeUpdate();
    }
    public void delete(Long notificationIdx){
        em.createQuery("delete from Notification m where m.notification_id=:notificationIdx")
                .setParameter("notificationIdx",notificationIdx)
                .executeUpdate();
    }
    public Notification findById(Long id){
        return em.find(Notification.class,id);
    }
    public List<Notification> findAll(){
        return em.createQuery("select m from Notification m", Notification.class)
                .getResultList();
    }
    public List<Notification> findByGoods(){
        return em.createQuery("select m from Notification m order by m.goods desc", Notification.class)
                .getResultList();
    }
    public List<Notification> findByUser(String email){
        return em.createQuery("select m from Notification m where m.member.email=:email", Notification.class)
                .setParameter("email",email)
                .getResultList();
    }
    public List<Notification> findBySearch(String keyWord){
        keyWord="%"+keyWord+"%";
        return em.createQuery("select m from Notification m where m.title like: keyWord or m.content like: keyWord", Notification.class)
                .setParameter("keyWord",keyWord)
                .getResultList();
    }
    public List<Notification> findByNewer(){
        return em.createQuery("select m from Notification m order by m.notification_id desc", Notification.class)
                .getResultList();//아이디가 클 수록 늦게 올라온거이므로 내림차순으로 정렬하면 최신순으로 가져올 수 있음
    }
}

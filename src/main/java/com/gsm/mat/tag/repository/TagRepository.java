package com.gsm.mat.tag.repository;

import com.gsm.mat.tag.Tag;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class TagRepository {
    @PersistenceContext
    EntityManager em;
    public Long save(Tag tag){
        em.persist(tag);
        return tag.getId();
    }
    public List<Tag> findByNotification(Long notificationIdx){
        return em.createQuery("select m from Tag m where m.notification.notification_id=:notificationIdx",Tag.class)
                .setParameter("notificationIdx",notificationIdx)
                .getResultList();
    }
    public void delete(Long tagIdx){
        em.createQuery("delete from Tag m where m.id=:tagIdx")
                .setParameter("tagIdx",tagIdx)
                .executeUpdate();
    }
}

package com.gsm.mat.notification.service;

import com.gsm.mat.exceptionAdvice.exception.UserNotFoundException;
import com.gsm.mat.member.Member;
import com.gsm.mat.member.repository.MemberRepository;
import com.gsm.mat.member.service.MemberService;
import com.gsm.mat.notification.Notification;
import com.gsm.mat.notification.dto.NotificationDto;
import com.gsm.mat.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final MemberRepository memberRepository;
    public Long create(NotificationDto notificationDto){
        Notification notification = notificationDto.toEntity();
        List<Member> byEmail = memberRepository.findByEmail(MemberService.getUserEmail());
        Member member = byEmail.get(0);
        notification.setMember(member);
        return notificationRepository.save(notification);
    }
    public void updateNotification(Long notificationIdx, NotificationDto notificationDto){
        Notification notification = notificationDto.toEntity();
        Member member = findOne(notificationIdx).getMember();
        Member findMember = memberRepository.findByEmail(MemberService.getUserEmail()).get(0);
        if(member==findMember){
            notificationRepository.update(notification,findOne(notificationIdx));
        }
        else{
            throw new UserNotFoundException();
        }
    }
    public void deleteNotification(Long notificationIdx){
        Member findMember = memberRepository.findByEmail(MemberService.getUserEmail()).get(0);
        if(findOne(notificationIdx).getMember()==findMember){
            notificationRepository.delete(notificationIdx);
        }
        else{
            throw new UserNotFoundException();
        }
    }
    @Transactional(readOnly = true)
    public List<Notification> findAll(){
        return notificationRepository.findAll();
    }
    @Transactional(readOnly = true)
    public Notification findOne(Long id){
        return notificationRepository.findById(id);
    }
    @Transactional(readOnly = true)
    public List<Notification> findByGoods(){
        return notificationRepository.findByGoods();
    }
    @Transactional(readOnly = true)
    public List<Notification> findMy() {
        return notificationRepository.findByUser(MemberService.getUserEmail());
    }
    @Transactional(readOnly = true)
    public List<Notification> findByKeyWord(String keyWord){
        return notificationRepository.findBySearch(keyWord);
    }
    @Transactional(readOnly = true)
    public List<Notification> findByNew(){return notificationRepository.findByNewer();}

    public void addGoods(Long id){
        Notification notification = notificationRepository.findById(id);
        notification.updateGoods(notification.getGoods()+1);
    }
    public void minusGoods(Long id){
        Notification notification = notificationRepository.findById(id);
        notification.updateGoods(notification.getGoods()-1);
    }
}

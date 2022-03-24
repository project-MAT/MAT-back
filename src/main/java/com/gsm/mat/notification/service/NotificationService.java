package com.gsm.mat.notification.service;

import com.gsm.mat.exception.ErrorCode;
import com.gsm.mat.exception.exception.NotMinusGoodsException;
import com.gsm.mat.exception.exception.NotificationNotFindException;
import com.gsm.mat.exception.exception.UserNotFoundException;
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
    private final MemberService memberService;

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
            notification.update(findOne(notificationIdx));
        }
        else{
            throw new UserNotFoundException("User can't find", ErrorCode.USER_NOT_FOUND);
        }
    }
    public void deleteNotification(Long notificationIdx){
        Member findMember = memberRepository.findByEmail(MemberService.getUserEmail()).get(0);
        if(findOne(notificationIdx).getMember()==findMember){
            notificationRepository.delete(notificationIdx);
        }
        else{
            throw new UserNotFoundException("User can't find", ErrorCode.USER_NOT_FOUND);
        }
    }
    @Transactional(readOnly = true)
    public List<Notification> findAll(){
        return notificationRepository.findAll();
    }
    @Transactional(readOnly = true)
    public Notification findOne(Long id){
        Notification byId = notificationRepository.findById(id);
        if(byId == null){
            throw new NotificationNotFindException("notification can't find", ErrorCode.NOTIFICATION_NOT_FIND);
        }
        return byId;
    }
    @Transactional(readOnly = true)
    public List<Notification> findByGoods(){
        return notificationRepository.findByGoods();
    }
    @Transactional(readOnly = true)
    public List<Notification> findMy() {
        return memberService.findByEmail(MemberService.getUserEmail()).getNotifications();
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
        if(notification.getGoods()>0){notification.updateGoods(notification.getGoods()-1);}
        else {throw new NotMinusGoodsException("this notification's goods are smaller than 1", ErrorCode.CAN_NOT_MINUS);}
    }
}

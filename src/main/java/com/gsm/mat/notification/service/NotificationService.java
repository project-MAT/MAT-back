package com.gsm.mat.notification.service;

import com.gsm.mat.exception.ErrorCode;
import com.gsm.mat.exception.exception.NotMinusGoodsException;
import com.gsm.mat.exception.exception.NotificationNotFindException;
import com.gsm.mat.exception.exception.UserNotFoundException;
import com.gsm.mat.member.Member;
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
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final MemberService memberService;

    @Transactional
    public Long create(NotificationDto notificationDto){
        Notification notification = notificationDto.toEntity();
        Member member = memberService.findByEmail(MemberService.getUserEmail());
        notification.setMember(member);
        return notificationRepository.save(notification).getNotification_id();
    }

    @Transactional
    public void updateNotification(Long notificationIdx, NotificationDto notificationDto){
        Notification notification = notificationDto.toEntity();
        Member member = findOne(notificationIdx).getMember();
        Member findMember = memberService.findByEmail(MemberService.getUserEmail());
        if(member==findMember){
            notification.update(findOne(notificationIdx));
        }
        else{
            throw new UserNotFoundException("User can't find", ErrorCode.USER_NOT_FOUND);
        }
    }

    @Transactional
    public void deleteNotification(Long notificationIdx){
        Member member = memberService.findByEmail(MemberService.getUserEmail());
        if(findOne(notificationIdx).getMember()==member){
            Notification notification = notificationRepository.findById(notificationIdx)
                    .orElseThrow(() -> new NotificationNotFindException("Notification can't find", ErrorCode.NOTIFICATION_NOT_FIND));
            notificationRepository.delete(notification);
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
        Notification byId = notificationRepository.findById(id)
                .orElseThrow(() -> new NotificationNotFindException("Notification can't find", ErrorCode.NOTIFICATION_NOT_FIND));
        if(byId == null){
            throw new NotificationNotFindException("notification can't find", ErrorCode.NOTIFICATION_NOT_FIND);
        }
        return byId;
    }

    @Transactional(readOnly = true)
    public List<Notification> findByGoods(){
        return notificationRepository.findAllOrderByGoods();
    }
    @Transactional(readOnly = true)
    public List<Notification> findMy() {
        return memberService.findByEmail(MemberService.getUserEmail()).getNotifications();
    }
    @Transactional(readOnly = true)
    public List<Notification> findByKeyWord(String keyWord){
        return notificationRepository.findAllByTitleLike(keyWord);
    }
    @Transactional(readOnly = true)
    public List<Notification> findByNew(){return notificationRepository.findAllOrderByNewer();}

    public void addGoods(Long id){
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(()->new NotificationNotFindException("Notification can't find", ErrorCode.NOTIFICATION_NOT_FIND));
        notification.updateGoods(notification.getGoods()+1);
    }
    public void minusGoods(Long id){
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(()->new NotificationNotFindException("Notification can't find", ErrorCode.NOTIFICATION_NOT_FIND));
        if(notification.getGoods()>0){notification.updateGoods(notification.getGoods()-1);}
        else {throw new NotMinusGoodsException("this notification's goods are smaller than 1", ErrorCode.CAN_NOT_MINUS);}
    }
}

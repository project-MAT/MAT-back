package com.gsm.mat.notification.controller;

import com.gsm.mat.notification.Notification;
import com.gsm.mat.notification.dto.NotificationDto;
import com.gsm.mat.notification.service.NotificationService;
import com.gsm.mat.response.ResponseService;
import com.gsm.mat.response.result.CommonResult;
import com.gsm.mat.response.result.ListResult;
import com.gsm.mat.response.result.SingleResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class NotificationController {
    private final NotificationService notificationService;
    private final ResponseService responseService;
    @PostMapping("/notification")
    public CommonResult join( @RequestBody NotificationDto notificationDto){
        notificationService.create(notificationDto);
        return responseService.getSuccessResult();
    }
    @PutMapping("/notification/{notificationIdx}")
    public CommonResult fixNotification(@RequestBody NotificationDto notificationDto, @PathVariable Long notificationIdx){
        notificationService.updateNotification(notificationIdx, notificationDto);
        return responseService.getSuccessResult();
    }
    @DeleteMapping("/notification/{notificationIdx}")
    public CommonResult deleteNotification(@PathVariable Long notificationIdx){
        notificationService.deleteNotification(notificationIdx);
        return responseService.getSuccessResult();
    }
    @GetMapping("/notification")
    public ListResult<Notification> getAllNotification(){
        return responseService.getListResult(notificationService.findAll());
    }
    @GetMapping("/notification/{notificationIdx}")
    public SingleResult<Notification> getNotificationById(@PathVariable Long notificationIdx){
        return responseService.getSingleResult(notificationService.findOne(notificationIdx));
    }
    @GetMapping("/notification/rank/like")
    public ListResult<Notification> getNotificationByGoods(){
        return responseService.getListResult(notificationService.findByGoods());
    }
    @GetMapping("/notification/rank/new")
    public ListResult<Notification> getNotificationByNew(){
        return responseService.getListResult(notificationService.findByNew());
    }
    @GetMapping("/notification/my")
    public ListResult<Notification> getNotificationMy(){
        return responseService.getListResult(notificationService.findMy());
    }
    @GetMapping("/notification/search")
    public ListResult<Notification> getNotificationSearch(@RequestParam("keyWord") String keyWord){
        return responseService.getListResult(notificationService.findByKeyWord(keyWord));
    }
    @PutMapping("/notification/like/increase/{notificationIdx}")
    public CommonResult likeNotification(@PathVariable Long notificationIdx){
        notificationService.addGoods(notificationIdx);
        return responseService.getSuccessResult();
    }
    @PutMapping("/notification/like/decrease/{notificationIdx}")
    public CommonResult dislikeNotification(@PathVariable Long notificationIdx){
        notificationService.minusGoods(notificationIdx);
        return responseService.getSuccessResult();
    }
}

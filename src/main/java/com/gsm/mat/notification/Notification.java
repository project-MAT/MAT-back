package com.gsm.mat.notification;

import com.gsm.mat.member.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter @Setter
public class Notification {
    @Id @GeneratedValue
    private Long notification_id;
    private String title;
    private String content;
    private int goods=0;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="member_id",nullable = false)
    private Member member;

    public Notification() {}


    public void updateGoods(int goods){
        this.goods=goods;
    }
}

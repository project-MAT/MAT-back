package com.gsm.mat.notification;

import com.gsm.mat.member.Member;
import com.gsm.mat.tag.Tag;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Builder
@Getter @Setter
public class Notification {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long notification_id;
    private String title;
    private String content;
    private int goods;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id",nullable = false)
    private Member member;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "notification_id")
    private List<Tag> tags;


    public void updateGoods(int goods){
        this.goods=goods;
    }

    public void update(Notification notification){
        this.title=notification.getTitle();
        this.content=notification.getContent();
    }
}

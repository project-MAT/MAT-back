package com.gsm.mat.notification;

import com.gsm.mat.member.Member;
import com.gsm.mat.tag.Tag;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@Builder
@Getter @Setter
public class Notification {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long notification_id;
    private String title;
    private String content;
    private int goods;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="member_id",nullable = false)
    private Member member;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "notification_id")
    private List<Tag> tags;

    public Notification() {}


    public void updateGoods(int goods){
        this.goods=goods;
    }
}

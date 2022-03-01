package com.gsm.mat.tag;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gsm.mat.notification.Notification;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tag_id")
    private long id;

    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="notification_id")
    @JsonIgnore
    private Notification notification;

    public Tag(){}
    public Tag(String content) {
        this.content = content;
    }
}

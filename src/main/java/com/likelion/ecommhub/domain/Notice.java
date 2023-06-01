package com.likelion.ecommhub.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class Notice extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private NoticeType noticeType;
    private String title;
    private String content;

    public Notice(Long id, NoticeType noticeType, String title, String content) {
        this.id = id;
        this.noticeType = noticeType;
        this.title = title;
        this.content = content;
    }

}

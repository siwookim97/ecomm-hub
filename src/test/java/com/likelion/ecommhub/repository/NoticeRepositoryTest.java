package com.likelion.ecommhub.repository;

import com.likelion.ecommhub.domain.Notice;
import com.likelion.ecommhub.domain.NoticeType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@SpringBootTest
@Transactional
class NoticeRepositoryTest {

    @Autowired
    NoticeRepository noticeRepository;

    @Test
    @DisplayName("Notice 엔티티 생성 테스트")
    void save() {
        Notice notice1 = new Notice(1L, NoticeType.SYSTEM, "title1", "content1");
        Notice notice2 = new Notice(2L, NoticeType.GENERAL, "title2", "content2");
        Notice notice3 = new Notice(3L, NoticeType.SYSTEM, "title3", "content3");

        noticeRepository.save(notice1);
        noticeRepository.save(notice2);
        noticeRepository.save(notice3);

        List<Notice> notices = noticeRepository.findAll();

        Assertions.assertThat(notices.size()).isEqualTo(3);

    }


}
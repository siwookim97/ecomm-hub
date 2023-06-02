package com.likelion.ecommhub.Service;

import com.likelion.ecommhub.domain.NoticeType;
import com.likelion.ecommhub.dto.NoticeDto;
import com.likelion.ecommhub.repository.NoticeRepository;
import com.likelion.ecommhub.service.NoticeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class NoticeServiceTest {

    @Autowired
    NoticeService noticeService;
    @Autowired
    NoticeRepository noticeRepository;


    @BeforeEach
    void init(){
        NoticeDto noticeDto1 = new NoticeDto(NoticeType.SYSTEM,"제목1","내용");
        NoticeDto noticeDto2 = new NoticeDto(NoticeType.SYSTEM,"제목2","내용");
        NoticeDto noticeDto3 = new NoticeDto(NoticeType.SYSTEM,"제목3","내용");

        noticeService.register(noticeDto1);
        noticeService.register(noticeDto2);
        noticeService.register(noticeDto3);
    }

    @Test
    @DisplayName("공지 등록 성공")
    void register(){
        NoticeDto noticeDto4 = new NoticeDto(NoticeType.SYSTEM,"제목4","내용");

        String registerResult = noticeService.register(noticeDto4);

        Assertions.assertThat(registerResult).isEqualTo("공지 등록 완료");
    }

    @Test
    @DisplayName("같은 제목의 공지 등록 ")
    void registerSameTitle(){
        NoticeDto noticeDto4 = new NoticeDto(NoticeType.SYSTEM,"제목1","내용");

        String registerResult = noticeService.register(noticeDto4);

        Assertions.assertThat(registerResult).isEqualTo("같은 제목의 공지가 존재합니다");
    }
}
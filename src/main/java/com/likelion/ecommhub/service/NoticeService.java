package com.likelion.ecommhub.service;

import com.likelion.ecommhub.domain.Notice;
import com.likelion.ecommhub.dto.NoticeDto;
import com.likelion.ecommhub.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    @Transactional
    public String register(NoticeDto noticeDto) {

        if (findSameTitle(noticeDto.getTitle())) {
            return "같은 제목의 공지가 존재합니다";
        }

        Notice notice = create(noticeDto);
        noticeRepository.save(notice);
        return "공지 등록 완료";
    }

    @Transactional
    public String delete(Notice notice){
        noticeRepository.delete(notice);
        return "삭제에 성공하였습니다";
    }

    private Notice create(NoticeDto noticeDto) {
        return new Notice(
                noticeDto.getNoticeType(),
                noticeDto.getTitle(),
                noticeDto.getContent()
        );
    }

    private boolean findSameTitle(String title) {
        Optional<Notice> findResult = noticeRepository.findByTitle(title);
        return findResult.isPresent();
    }
}

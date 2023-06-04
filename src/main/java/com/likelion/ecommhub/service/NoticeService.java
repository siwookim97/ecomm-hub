package com.likelion.ecommhub.service;

import com.likelion.ecommhub.domain.Notice;
import com.likelion.ecommhub.dto.NoticeDto;
import com.likelion.ecommhub.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    public String delete(Long id) {
        Optional<Notice> existingNotice = noticeRepository.findById(id);
        if (existingNotice.isEmpty()) {
            return "존재하지 않습니다";
        }
        noticeRepository.delete(existingNotice.get());
        return "삭제에 성공하였습니다";
    }

    @Transactional
    public String modify(Long id, NoticeDto noticeDto) {

        Optional<Notice> existingNotice = noticeRepository.findById(id);
        if (existingNotice.isEmpty()) {
            return "존재하지 않습니다";
        }
        Notice notice = existingNotice.get();
        notice.update(noticeDto.getNoticeType(), noticeDto.getTitle(), noticeDto.getContent());

        return "업데이트에 성공했습니다";
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

    public List<Notice> getAllNotice() {
        return noticeRepository.findAll();
    }

    public Notice findById(Long id){
        Optional<Notice> byId = noticeRepository.findById(id);
        return byId.orElse(null);
    }
}

package com.likelion.ecommhub.controller;

import com.likelion.ecommhub.domain.Notice;
import com.likelion.ecommhub.dto.NoticeDto;
import com.likelion.ecommhub.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("/list")
    public String showList(Model model) {
        List<Notice> allNotice = noticeService.getAllNotice();
        model.addAttribute("notices", allNotice);

        return "notice/list";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("noticeDto",new NoticeDto());
        return "notice/form";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    public String create(@ModelAttribute("noticeDto") @Valid NoticeDto noticeDto ) {
        noticeService.register(noticeDto);

        return "redirect:/notice/list";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/delete")
    public String delete(@RequestParam("noticeId") Long id) {
        String result = noticeService.delete(id);

        return "redirect:/notice/list";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/modify/{noticeId}")
    public String showModifyForm(@PathVariable Long noticeId, Model model) {
        Notice notice = noticeService.findById(noticeId);
        model.addAttribute("notice", notice);
        model.addAttribute("noticeDto", new NoticeDto());

        return "notice/edit-form";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/modify/{noticeId}")
    public String modify(@PathVariable Long noticeId, @Valid NoticeDto noticeDto) {
        noticeService.modify(noticeId,noticeDto);

        return "redirect:/notice/list";
    }
}
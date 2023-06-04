package com.likelion.ecommhub.controller;

import com.likelion.ecommhub.domain.Notice;
import com.likelion.ecommhub.dto.NoticeDto;
import com.likelion.ecommhub.service.NoticeService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("noticeDto",new NoticeDto());
        return "notice/form";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("noticeDto") @Valid NoticeDto noticeDto ) {
        noticeService.register(noticeDto);
        return "redirect:/notice/list";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("noticeId") Long id) {
        String result = noticeService.delete(id);
        System.out.println(result);
        return "redirect:/notice/list";
    }

    @GetMapping("/modify/{id}")
    public String showModifyForm(@PathVariable Long id,Model model) {
        Notice notice = noticeService.findById(id);
        model.addAttribute("notice", notice);
        model.addAttribute("noticeDto", new NoticeDto());
        return "notice/edit-form";
    }

    @PostMapping("/modify/{id}")
    public String modify(@PathVariable Long id, @Valid NoticeDto noticeDto) {
        noticeService.modify(id,noticeDto);
        return "redirect:/notice/list";
    }
}

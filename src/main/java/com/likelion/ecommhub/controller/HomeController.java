package com.likelion.ecommhub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "redirect:/product/home";
    }

    @GetMapping("/t")
    public String test() {
        return "usr/member/test";
    }
}

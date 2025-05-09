package com.example.security.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

// controller 수정하면 로그인 풀림
@Log4j2
@Controller
public class HomeController {

    @GetMapping("/")
    public String getHome() {
        log.info("home");
        return "home";
    }

    // 로그인에 성공하면 그 정보를 SecurityContextHolder에 저장함
    // 저장된 정보 확인
    @ResponseBody
    @GetMapping("/auth")
    public Authentication gAuthentication() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        return authentication;
    };
}

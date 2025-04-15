package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Log4j2
@Controller

public class HomeController {

    // http://localhost:8080/ ==> /
    @GetMapping("/")
    public String getHome() {
        log.info("home 요청"); // == sysout -> @Log4j2 해줘야 함
        return "home";
    }

    // http://localhost:8080/basic
    @GetMapping("/basic")
    public String getMethodName() {
        return "info"; // 템플릿 이름(html)
    }

    @PostMapping("/basic")
    public String postAdd(@ModelAttribute("num1") int num1, @ModelAttribute("num2") int num2, Model model) {
        log.info("덧셈 요청 {},{}", num1, num2);
        // 덧셈 결과를 info로 전송
        int result = num1 + num2;

        // keyName : 화면 단에서 불러서 사용 => 중복 안됨
        // model.addAttribute("keyName", value); -> 화면단에서 사용
        // model.addAttribute("result", result);
        model.addAttribute("result", result);
        // model.addAttribute("num1", num1);
        // model.addAttribute("num2", num2);

        return "info";
    }

}

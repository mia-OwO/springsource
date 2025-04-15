package com.example.demo.controller;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Log4j2
@RequestMapping("/board") // 중복된 경로 처리
@Controller

public class BordController {
    @GetMapping("/create")
    public void getCreate() {
        // return "board/create"; // 진짜 경로
    }

    @PostMapping("/create")
    // public String postCreate(@ModelAttribute("name") String name,
    // RedirectAttributes rttr) {
    // log.info("name 값 가져오기{}", name);

    // // 어느 페이지로 이동을 하던지 간에 name 유지시키고 싶다면
    // // 커멘드객체, ModelAttribute(or @ModelAttribute)
    // // return "board/list";
    // // return "redirect:/board/list"; // 경로를 새로 타서 가져오는거라 값이 소멸되는게 맞음

    // // redirect값 유지하고 싶다면? --> RedirectAttributes 사용하기(addAttribute) + parma.name
    // // rttr.addAttribute("name", name);

    // // or FlashAttribute + name
    // rttr.addFlashAttribute("name", name);
    // return "redirect:/board/list";
    // }

    public void postCreate(String name, HttpSession session) {
        log.info("name 값 가져오기{}", name);

        session.setAttribute("name1", name);
    }

    @GetMapping("/list")
    public void getList() {
        // return "board/list";
    }

    @GetMapping("/read")
    public void getRead() {
        // return "board/read";
    }

    @GetMapping("/update")
    public void getUpdate() {
        // return "board/update";
    }
}

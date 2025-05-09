package com.example.security.controller;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequestMapping("/sample")
public class SampleController {

    @GetMapping("/guest")
    public void getGest() {
        log.info("guest");

    }

    @GetMapping("/member")
    public void getMember() {
        log.info("member");

    }

    @GetMapping("/admin")
    public void getAdmin() {
        log.info("admin");

    }

}
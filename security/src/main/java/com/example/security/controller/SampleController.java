package com.example.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequestMapping("/sample")
public class SampleController {

    @PreAuthorize("permitAll()")
    @GetMapping("/guest")
    public void getGest() {
        log.info("guest");

    }

    @PreAuthorize("hasAnyRole('ADMIN','USER','MANAGER')")
    @GetMapping("/member")
    public void getMember() {
        log.info("member");

    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/admin")
    public void getAdmin() {
        log.info("admin");

    }

}
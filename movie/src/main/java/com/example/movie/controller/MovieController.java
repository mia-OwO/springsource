package com.example.movie.controller;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequestMapping("movie")
public class MovieController {
    @GetMapping("/list")
    public void getList() {
        log.info("영화 리스트요청");
    }

}

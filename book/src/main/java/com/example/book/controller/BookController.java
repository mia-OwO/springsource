package com.example.book.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.book.dto.BookDTO;

import org.springframework.web.bind.annotation.PostMapping;

@Log4j2
@Controller
@RequestMapping("/book")
public class BookController {

    @GetMapping("/list")
    public void getList() {
        log.info("book list 요청");
    }

    @GetMapping({ "/read", "/modify" })
    public void getRead(Long code) {
        log.info("book get요청 {}", code);
    }

    @PostMapping("/modify")
    public void postModiry(BookDTO dto) {
        log.info("book modify요청 {}", dto);
    }

    @PostMapping("/remove")
    public void postRemove(Long code) {
        log.info("book remove요청 {}", code);
    }

}

package com.example.rest.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.rest.dto.BookDTO;
import com.example.rest.dto.PageRequestDTO;
import com.example.rest.dto.PageResultDTO;
import com.example.rest.service.BookService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

// 일반 컨트롤러 + REST
// 데이터만 내보내기 : ResponseEntity or @ResponseBody

@Log4j2
@Controller
@RequiredArgsConstructor // service사용
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    @GetMapping("/create")
    public void getCreate(@ModelAttribute("book") BookDTO dto, PageRequestDTO pageRequestDTO) {
        log.info("도서작성폼요청");
    }

    @ResponseBody
    @PostMapping("/create")
    public Long postCreate(@RequestBody BookDTO dto) {
        log.info("도서작성폼요청");

        Long code = bookService.insert(dto);

        return code;
    }

    @GetMapping("/list")
    public ResponseEntity<PageResultDTO<BookDTO>> getList(PageRequestDTO pageRequestDTO, Model model) {

        log.info("book list 요청 {}", pageRequestDTO);
        PageResultDTO<BookDTO> pageResultDTO = bookService.readAll(pageRequestDTO);
        return new ResponseEntity<>(pageResultDTO, HttpStatus.OK);
    }

    // @PathVariable + /{code} --> book/read/100
    @ResponseBody
    @GetMapping({ "/read/{code}", "/modify/{code}" })
    public BookDTO getRead(@PathVariable Long code, PageRequestDTO pageRequestDTO, Model model) {
        log.info("book get요청 {}", code);

        BookDTO book = bookService.read(code);
        return book;

    }

    @ResponseBody
    @PutMapping("/modify")
    public Long postModify(@RequestBody BookDTO dto, PageRequestDTO pageRequestDTO) {
        log.info("book modify요청 {}", dto);

        // service 호출
        bookService.modify(dto);
        // read
        // rttr.addAttribute("code", dto.getCode());

        return dto.getCode();

    }

    // 동일한 방식(post), 동일한 경로(/remove) -> error(illegalStateException)

    @DeleteMapping("/remove/{code}")
    public ResponseEntity<Long> postRemove(@PathVariable Long code, PageRequestDTO pageRequestDTO) {
        log.info("book remove요청 {}", code);

        // 서비스 호출
        bookService.remove(code);

        return new ResponseEntity<Long>(code, HttpStatus.OK);

    }

    // @PostMapping("/remove")
    // public void postRemove1(Long code) {
    // log.info("book remove요청 {}", code);
    // }
}

package com.example.rest.controller;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.rest.dto.MemoDTO;

import com.example.rest.service.MemoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@Log4j2
@RestController
@RequestMapping("/memo")
@RequiredArgsConstructor
public class MemoController {

    private final MemoService memoService;

    @GetMapping("/list")
    public List<MemoDTO> getList(Model model) {
        List<MemoDTO> list = memoService.getList();

        return list;

    }

    @GetMapping(value = { "/read", "/update" })
    public MemoDTO getRow(Long mno, Model model) {
        log.info("조회 요청 {}", mno);
        MemoDTO dto = memoService.getRow(mno);

        return dto;

    }

    // (@RequestBody : json으로 들어올거야
    @PutMapping("/update")
    public Long postUpdate(@RequestBody MemoDTO dto) {
        log.info("메모 수정 {}", dto);
        Long mno = memoService.memoUpdate(dto);
        return mno;
    }

    // memo 추가 : /memo/new
    // get: 클릭하면
    @GetMapping("/new")
    public void getNew() {
        log.info("새 메모 작성 폼 요청");

    }

    // post: 버튼 클릭할 떄
    @PostMapping("/new")
    public Long postNew(@RequestBody MemoDTO dto) {
        // 사용자 입력값 가져오기
        log.info("새 메모 작성 {}", dto);
        Long mno = memoService.memoCreate(dto);

        return mno;

    }

    // memo 삭제 : /memo/remove/3
    @DeleteMapping("/remove/{mno}")
    public Long getRemove(@PathVariable Long mno) {
        log.info("memo 삭제 요청 {}", mno);

        // 삭제 요청
        memoService.memoDelete(mno);

        return mno;

    }

}

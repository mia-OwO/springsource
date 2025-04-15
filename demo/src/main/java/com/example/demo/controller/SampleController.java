package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.ui.Model;

import com.example.demo.dto.SampleDTO;

@Controller
@Log4j2
public class SampleController {

    @GetMapping("/sample")
    public void getSample(Model model) {
        log.info("smaple  페이지 요청");
        model.addAttribute("name", "hong");

        SampleDTO sampleDTO = SampleDTO.builder()
                .id(1L)
                .first("hong")
                .last("dong")
                .regDateTime(LocalDateTime.now())
                .build();

        model.addAttribute("dto", sampleDTO);

        List<SampleDTO> list = new ArrayList<>();
        for (long i = 0; i < 20; i++) {
            sampleDTO = SampleDTO.builder()
                    .id(i)
                    .first("hong" + i)
                    .last("dong" + i)
                    .regDateTime(LocalDateTime.now())
                    .build();
            list.add(sampleDTO);
        }

        model.addAttribute("list", list);
    }

}

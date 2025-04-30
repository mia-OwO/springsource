package com.example.board.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder

public class BoardDTO {

    private Long bno;
    private String title;
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    // Member
    private String email;
    private String name;

    // reply
    private Long replyCount;
}

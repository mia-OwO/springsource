package com.example.book.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class BookDTO {

    private Long code;

    private String title;

    private String author;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

}

package com.example.book.service;

import java.util.List;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.book.dto.BookDTO;
import com.example.book.entity.Book;
import com.example.book.repository.BookRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    public Long insert(BookDTO dto) {
        // dto => entity
        Book book = modelMapper.map(dto, Book.class);
        return bookRepository.save(book).getCode();

    }

    public BookDTO read(Long code) {
        Book book = bookRepository.findById(code).get();
        return modelMapper.map(book, BookDTO.class);
    }

    // modelMapper : 객체간 매칭 자동화(entity -> dto)
    // Collectors.toList(): 스트림의 결과를 리스트로 수집
    public List<BookDTO> readAll() {
        List<Book> list = bookRepository.findAll(); // Book entity 조회
        // entity => dto
        // modelMapper.map(book, BookDTO.class)
        List<BookDTO> books = list.stream().map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList()); // entity -> BookDTO 변환

        return books; // BookDTO -> list 반환
    }

    public void modify(BookDTO dto) {
        Book book = bookRepository.findById(dto.getCode()).get();
        book.setPrice(dto.getPrice());
        bookRepository.save(book);

    }

    public void remove(Long code) {
        // repository호출
        bookRepository.deleteById(code);
    }

}

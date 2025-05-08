package com.example.rest.service;

import java.util.List;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.rest.dto.BookDTO;
import com.example.rest.dto.PageRequestDTO;
import com.example.rest.dto.PageResultDTO;
import com.example.rest.entity.Book;
import com.example.rest.repository.BookRepository;

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
    public PageResultDTO<BookDTO> readAll(PageRequestDTO pageRequestDTO) {
        // List<Book> list = bookRepository.findAll(); // Book entity 조회

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize(),
                Sort.by("code").descending());

        Page<Book> result = bookRepository
                .findAll(bookRepository.makePredicate(pageRequestDTO.getType(), pageRequestDTO.getKeyword()), pageable);
        // entity => dto
        // modelMapper.map(book, BookDTO.class)

        List<BookDTO> books = result.get().map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());
        long totalCount = result.getTotalElements();
        return PageResultDTO.<BookDTO>withAll()
                .dtoList(books)
                .totalCount(totalCount)
                .pageRequestDTO(pageRequestDTO)
                .build();

        // List<BookDTO> books = list.stream().map(book -> modelMapper.map(book,
        // BookDTO.class))
        // .collect(Collectors.toList()); // entity -> BookDTO 변환

        // return books; // BookDTO -> list 반환
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

package com.example.rest.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.rest.dto.MemoDTO;
import com.example.rest.entity.Memo;
import com.example.rest.repository.MemoRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class MemoService {
    // Repository 메소드 호출한 후 결과 받기
    private final MemoRepository memoRepository;
    private final ModelMapper modelMapper;

    public List<MemoDTO> getList() {
        List<Memo> list = memoRepository.findAll();

        // Memo -> MemoDto로 옮기는 과정 필요

        // List<MemoDTO> memos = new ArrayList<>(); // 새로운 바구니 생성
        // // 옮기기
        // for (Memo memo : list) {
        // MemoDTO dto = MemoDTO.builder()
        // .mno(memo.getMno())
        // .memoText(memo.getMemoText())
        // .build();
        // memos.add(dto);

        // }

        // 좀 더 간단하게
        // list.stream().forEach(memo -> System.out.println(memo));
        // or
        List<MemoDTO> memos = list.stream()
                .map(memo -> entityToDto(memo)

                // .map(memo -> modelMapper.map(memo, MemoDTO.class)
                // MemoDTO dto = MemoDTO.builder()
                // .mno(memo.getMno())
                // .memoText(memo.getMemoText())
                // .build();
                // return dto;
                ).collect(Collectors.toList());
        return memos;
    }

    public MemoDTO getRow(Long mno) {
        Memo memo = memoRepository.findById(mno).orElseThrow(EntityNotFoundException::new);
        // entity => dto
        MemoDTO dto = entityToDto(memo);
        // modelMapper.map(원본,변경할 타입)
        // MemoDTO dto = modelMapper.map(memo, MemoDTO.class);
        return dto;
    }

    // 많이 하는거 따로 선언
    private MemoDTO entityToDto(Memo memo) {
        MemoDTO dto = MemoDTO.builder()
                .mno(memo.getMno())
                .memoText(memo.getMemoText())
                .createdDate(memo.getCreateDate())
                .updatedDate(memo.getCreateDate())
                .build();
        // MemoDTO dto = new MemoDTO();
        // dto.setMno(memo.getMno());
        // dto.setMemoText(memo.getMemoText());
        return dto;
    }

    public Long memoUpdate(MemoDTO dto) {
        Memo memo = memoRepository.findById(dto.getMno()).orElseThrow(EntityNotFoundException::new);
        memo.changeMemoText(dto.getMemoText());
        // update실행 => 수정된 Memo return
        memo = memoRepository.save(memo); // 여기서 끝내도 됨
        return memo.getMno();
    }

    public void memoDelete(Long mno) {
        memoRepository.deleteById(mno); // @Id 붙은게 findById
    }

    public Long memoCreate(MemoDTO dto) {
        // 새로 입력할 memo가 MemoDto안에 들어있음
        // MemoDtO => Memo 변환

        Memo memo = dtoToEntity(dto);
        // Memo memo = modelMapper.map(dto, Memo.class);
        // 새로저장한 memo 리턴됨
        memo = memoRepository.save(memo);
        return memo.getMno();
    }

    private Memo dtoToEntity(MemoDTO memoDTO) {
        Memo memo = Memo.builder()
                .mno(memoDTO.getMno())
                .memoText(memoDTO.getMemoText())

                .build();

        return memo;
    }

}

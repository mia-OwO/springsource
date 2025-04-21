package com.example.relation.repository;

import java.io.InputStream;
import java.util.concurrent.locks.Lock;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.relation.entity.sports.Locker;
import com.example.relation.entity.sports.SportsMember;
import com.example.relation.repository.sports.LockerRepository;
import com.example.relation.repository.sports.SportsMemberRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
public class LockerRepositoryTest {

    @Autowired
    private LockerRepository lockerRepository;

    @Autowired
    private SportsMemberRepository sportsMemberRepository;

    // 단방향(SportsMember => Locker)
    @Test
    public void testInsert() {
        // locker 생성

        IntStream.range(1, 6).forEach(i -> {
            Locker locker = Locker.builder().name("locker" + i).build();
            lockerRepository.save(locker); // locker생성
        });

        // 스포츠 회원 생성
        LongStream.range(1, 6).forEach(i -> {
            SportsMember sportsMember = SportsMember.builder().locker(Locker.builder().id(i).build()).name("member" + i)
                    .build();
            sportsMemberRepository.save(sportsMember);
        });

    }

    // 개별 조회
    @Test
    public void testRead1() {
        System.out.println(lockerRepository.findById(1L).get());
        System.out.println(sportsMemberRepository.findById(1L).get());
    }

    @Transactional
    @Test
    public void testRead2() {

        SportsMember sportsMember = sportsMemberRepository.findById(1L).get();

        System.out.println(sportsMember);
        System.out.println(sportsMember.getLocker());

    }

    @Test
    public void testUpdate() {
        // 3번 회원의 이름을 홍길동으로 변환

        SportsMember sportsMember = sportsMemberRepository.findById(3L).get(); // 변경할 id가져오기

        sportsMember.setName("홍길동"); // 바꾸고
        sportsMemberRepository.save(sportsMember); // 저장
    }

    @Test
    public void testDelete1() {
        // 5번 회원 삭제
        sportsMemberRepository.deleteById(5L);
    }

    @Test
    public void testDelete2() {
        // 4번 locker 삭제 -> 무결성 제약조건 - 4번 locker를 할당받은 member가 존재하기 때문
        // lockerRepository.deleteById(4L);

        // 4번회원에서 5번 locker할당
        // 4번 locker제거

        SportsMember member = sportsMemberRepository.findById(4L).get(); // 바꿀 회원
        Locker locker = lockerRepository.findById(5L).get(); // 할당할 locker

        member.setLocker(locker); // 할당
        sportsMemberRepository.save(member); // 저장

        lockerRepository.deleteById(4L); // 삭제

    }

    // ----------------------------------------------------------------
    // locker => sportsMember 접근
    // ----------------------------------------------------------------

    @Test
    public void testRead3() {

        Locker locker = lockerRepository.findById(1L).get();

        System.out.println(locker);
        System.out.println(locker.getSportsMember());

    }

}
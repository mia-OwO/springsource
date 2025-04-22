package com.example.jpa.repository;

import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Student;
import com.example.jpa.entity.Student.Grade;

import jakarta.persistence.EntityNotFoundException;

@SpringBootTest // test용 클래스임
public class StudentRepositoryTest {
    @Autowired // == new studentRepository()
    private StudentRepository studentRepository;

    // CRUD test
    // Repository, Entity 확인
    // C(insert) : save(Entity) : insert
    // U(update) : save(Entity) : update
    // 구별은 어떻게 하는가? 둘다 동일한 sav() 호출
    // 원본과 변경된 부분이 있다면 update로 실행해줌

    @Test // test메소드라는 의미(테스트 메소드는 리턴 타입 void여야 함)
    public void insertTest() {
        // Entity 생성

        LongStream.range(1, 11).forEach(i -> {
            Student student = Student.builder()
                    .name("홍길동" + i)
                    .grade(Grade.JUNIOR)
                    .gender("M")
                    .build();
            // insert
            studentRepository.save(student);

        });

    }

    @Test
    public void updateTest() {

        // findById(1L) : select * from 테이블명 where id=1; --> 실행

        Student student = studentRepository.findById(1L).get();
        student.setGrade(Grade.SENIOR);

        // update개념으로 save가 사용됨
        studentRepository.save(student);
    }

    @Test
    public void selectOneTest() {
        // Optional<Student> student = studentRepository.findById(1L);

        // if (student.isPresent()) {
        // System.out.println(student.get());
        // }

        // Student student = studentRepository.findById(1L).get(); // null 이 아닌게 확실 ->
        // get으로 바로 가져옴

        // Student student = studentRepository.findById(4L).get(); - error
        Student student = studentRepository.findById(9L).orElseThrow(EntityNotFoundException::new); // exception 선언
        System.out.println(student);

    }

    @Test
    public void selectTest() {
        // List<Student> list = studentRepository.findAll();

        // for (Student student : list) {
        // System.out.println(student);
        // }
        studentRepository.findAll().forEach(student -> System.out.println(student));
    }

    @Test
    public void deleteTest() {

        // 방법1
        // Student student = studentRepository.findById(2L).get();
        // studentRepository.delete(student);

        // 방법2
        studentRepository.deleteById(10L);
    }
}

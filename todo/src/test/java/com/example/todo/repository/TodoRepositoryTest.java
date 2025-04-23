package com.example.todo.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.todo.entity.ToDo;

@SpringBootTest
public class TodoRepositoryTest {
    @Autowired
    private TodoRepository todoRepository;

    @Test
    public void testInsert() {
        IntStream.rangeClosed(1, 10).forEach(i -> {
            // ToDo todo = ToDo.builder()
            // .content("강아지산책" + i)

            // .build();
            ToDo todo = new ToDo();
            todo.setContent("강아지산책" + i);

            todoRepository.save(todo);
        });

    }

    // 전체조회
    @Test
    public void testRead() {
        todoRepository.findAll().forEach(todo -> System.out.println(todo));
    }

    @Test
    public void testRead2() {
        // 완료 목록 추출(true인거 찾기)
        todoRepository.findByCompleted(true).forEach(todo -> System.out.println(todo));
    }

    @Test
    public void testRead3() {
        // 안중요 목록 추출(false인거 찾기)
        todoRepository.findByImportanted(false).forEach(todo -> System.out.println(todo));
    }

    @Test
    // 삭제
    public void testDelete() {
        todoRepository.deleteById(10L);

    }

    @Test
    // todo 수정 - 완료(1)
    public void testUpdate() {
        ToDo todo = todoRepository.findById(1L).get();
        todo.setCompleted(true);
        todoRepository.save(todo);

    }

}

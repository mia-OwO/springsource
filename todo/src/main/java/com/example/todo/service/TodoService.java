package com.example.todo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.todo.dto.ToDoDTO;
import com.example.todo.entity.ToDo;
import com.example.todo.repository.TodoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class TodoService {
    private final TodoRepository todoRepository;
    private final ModelMapper modelMapper;

    public Long create(ToDoDTO dto) {
        ToDo todo = modelMapper.map(dto, ToDo.class);

        return todoRepository.save(todo).getId();
    }

    public void remove(Long id) {
        todoRepository.deleteById(id);
    }

    public ToDoDTO read(Long id) {
        ToDo todo = todoRepository.findById(id).get();
        // entity => dtoㅇ변경후 리턴
        return modelMapper.map(todo, ToDoDTO.class);
    }

    public Long changeCompleted(ToDoDTO dto) {
        ToDo todo = todoRepository.findById(dto.getId()).get();
        todo.setCompleted(dto.isCompleted());
        return todoRepository.save(todo).getId();

    }

    public List<ToDoDTO> list(boolean completed) {

        List<ToDo> list = todoRepository.findByCompleted(completed);
        // ToDo entity => ToDoDTO 변경 후 리턴

        // List<TodoDTO> todos = new ArrayList<>();
        // list.forEach(todo -> {
        // TodoDTO dto = modelMapper.map(todo, TodoDTO.class);
        // todos.add(dto);
        // });

        // 간단하게
        List<ToDoDTO> todos = list.stream()
                .map(todo -> modelMapper.map(todo, ToDoDTO.class))
                .collect(Collectors.toList());
        return todos;
    }
}

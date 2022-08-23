package org.example.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.controller.TodoController;
import org.example.model.TodoEntity;
import org.example.model.TodoRequest;
import org.example.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

@WebMvcTest(TodoController.class)
class TodoControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    private TodoService todoService;

    private TodoEntity expected;

    //테스 메서드 실행 이전에 수행 하여 매번 아래의 값을 넣어준다.
    @BeforeEach
    void setup(){
        this.expected = new TodoEntity();

        this.expected.setId(123L);
        this.expected.setOrder(0L);
        this.expected.setCompleted(false);
        this.expected.setTitle("Test Title");
    }

    @Test
    void create() throws Exception{
        when(this.todoService.add(any(TodoRequest.class)))
                .then((i) -> {
                    TodoRequest request = i.getArgument(0, TodoRequest.class);
                    return new TodoEntity(this.expected.getId(), request.getTitle(), this.expected.getOrder(), this.expected.getCompleted());
                });

        TodoRequest request = new TodoRequest();
        request.setTitle("Any Title");

        ObjectMapper mapper = new ObjectMapper();
                                //object -> json으로 변경
        String content = mapper.writeValueAsString(request);

        //perform - 요청을 전송하는 역할, 결과로 ResultActions 객체를 받으며, ResultActions 객체는 리턴 값을 검증하고 확인할 수 있는 andExcpect() 메소드를 제공
        mvc.perform(post("/")
                    //json형식으로 데티터를 보낸다고 명시
                    .contentType(MediaType.APPLICATION_JSON)
                    //json형식을 String만들어준다.
                    .content(content))
                .andExpect(status().isOk())//Http 200을 기대
                .andExpect(jsonPath("$.title").value("Any Title"));
    }

    @Test
    void readOne(){

    }
}
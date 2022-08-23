package org.example.service;

import lombok.AllArgsConstructor;
import org.example.model.TodoEntity;
import org.example.model.TodoRequest;
import org.example.model.TodoResponse;
import org.example.repository.TodoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service//서비스는 기능을 구현한다.
@AllArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;

    //1 리스트 목록에 아이템 추가
    public TodoEntity add(TodoRequest request){
        TodoEntity todoEntity = new TodoEntity();

        todoEntity.setTitle(request.getTitle());
        todoEntity.setOrder(request.getOrder());
        todoEntity.setCompleted(request.getCompleted());

        return this.todoRepository.save(todoEntity);
    }

    //2 리스트 목록 중 특정 아이템 조회
    public TodoEntity searchById(Long id){
        return this.todoRepository.findById(id)
                //값이 없을 경우 Exception이 발생한다.
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    //3 리스트 전체 목록 조회
    public List<TodoEntity> searchAll(){
        return this.todoRepository.findAll();
    }

    //4 리스트 목록 중 특정 아이템 수정
                                //조회할 id, 변경할 내용 request
    public TodoEntity updateById(Long id, TodoRequest request){
        TodoEntity todoEntity = this.searchById(id);

        if(request.getTitle() != null)
            todoEntity.setTitle(request.getTitle());

        if(request.getOrder() != null)
            todoEntity.setOrder(request.getOrder());

        if(request.getCompleted() != null)
            todoEntity.setCompleted(request.getCompleted());

        return this.todoRepository.save(todoEntity);
    }

    //5 리스트 목록 중 특정 아이템 삭제
    public void deleteById(Long id){
        this.todoRepository.deleteById(id);
    }

    //6 리스트 전체 목록 삭제
    public void deleteAll(){
        this.todoRepository.deleteAll();
    }

}

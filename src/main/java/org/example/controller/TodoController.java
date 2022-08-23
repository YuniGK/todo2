package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.model.TodoEntity;
import org.example.model.TodoRequest;
import org.example.model.TodoResponse;
import org.example.service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@CrossOrigin//CROS 해결하는 방법
@AllArgsConstructor
@RestController
/* @Controller에 @ResponseBody가 추가된 기능
RestController의 주 용도는 Json 형태로 객체 데이터를 반환
최근에는 데이터 응답을 제공하는 REST API를 개발할 때 주로 사용하며 객체를 ResponseEntity로 감싸서 반환 */
@RequestMapping("/")//우리가 특정 URL을 보내면 어떠헥 처리할 것인지 정의한다.
public class TodoController {
    private final TodoService service;

    //POST 아이템 추가
    @PostMapping
    public ResponseEntity<TodoResponse> create(@RequestBody TodoRequest request){
        System.out.println("Create");

        //모든 Object 대응하며 값이 비웠는지 확인한다.
        if (ObjectUtils.isEmpty(request.getTitle()))
            //에러값이 있으면 badRequest를 리턴한다.
            return ResponseEntity.badRequest().build();

        if (ObjectUtils.isEmpty(request.getOrder()))
            request.setOrder(0L);

        if (ObjectUtils.isEmpty(request.getCompleted()))
            request.setCompleted(false);

        TodoEntity result = this.service.add(request);

        /* ResponseEntity - HttpEntity를 상속받음으로써 HttpHeader와 body를 가질 수 있으며,
                            결과 데이터와 HTTP 상태 코드를 직접 제어할 수 있다.
                            -> 서버 내부의 보안을 위해 상태 코드를 바꾸거나 body에 특정 메시지만 전송이 가능
        HttpEntity - HTTP 요청(Request) 또는 응답(Response)에 해당하는 HttpHeader와 HttpBody를 포함
        */
                            //.ok - 200 응답 코드 / 만약 body가 없을 경우 .ok().bulid();
                            //상태 코드와 body를 같이 보낸다.
        return ResponseEntity.ok(new TodoResponse(result));
    }

    //GET 전체 조회
    @GetMapping("{id}")
                                                //PathVariable - {템플릿 변수}와 동일한 이름을 갖는 파라미터를 추가
    public ResponseEntity<TodoResponse> readOne(@PathVariable Long id){
        System.out.println("Read One");

        TodoEntity result = this.service.searchById(id);

        return ResponseEntity.ok(new TodoResponse(result));
    }

    //GET 부분 조회
    @GetMapping
    public ResponseEntity<List<TodoResponse>> readAll(){
        System.out.println("Read All");

        List<TodoEntity> list = this.service.searchAll();
        List<TodoResponse> response = list.stream().map(TodoResponse::new)
                                        .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    //PATH 수정
    @PatchMapping("{id}")
    public ResponseEntity<TodoResponse> update(@PathVariable Long id, @RequestBody TodoRequest request){
        System.out.println("Update");

        TodoEntity result = this.service.updateById(id, request);

        return ResponseEntity.ok(new TodoResponse(result));
    }

    //DELETE 부분 삭제
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteOne(@PathVariable Long id){
        System.out.println("Deltet One");

        this.service.deleteById(id);

        return ResponseEntity.ok().build();
    }

    //DELETE 전체 삭제
    @DeleteMapping
    public ResponseEntity<?> deleteAll(){
        System.out.println("Delete All");

        this.service.deleteAll();

        return ResponseEntity.ok().build();
    }
}

package org.example.repository;

import org.example.model.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository//DB등 외부 작업을 처리
                                                                //id
public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
    //조회, 삭제, 수정 등의 작업의 쿼리를 대신 실행해 준다.
}

package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor//기본 생성장 생성
@AllArgsConstructor//모든 필드 값을 받는 생성자 생성
public class TodoEntity {

    @Id
    //primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /* primary key 값을 위한 자동생성 전략을 명시,
    GenerationType.AUTO 기본 설정 값, 특정 DB에 맞게 아래의 세가지 방법을 자동 선택
    GenerationType.IDENTITY 기본키 생성을 DB에 위임
    GenerationType.SEQUENCE DB가 자동으로 숫자를 생성
    GenerationType.TABLE 키 생성 전용 테이블을 하나 만들어서 시퀸스를 흉내낸다
    */
    private Long id;

    @Column(nullable = false)
    //column 속성 중 하나로 값 false 설정 시, not null 조건이 붙어 테이블이 생성된다.
    private String title;

    @Column(name = "todoOrder", nullable = false)
    private Long order;

    @Column(nullable = false)
    private Boolean completed;
}

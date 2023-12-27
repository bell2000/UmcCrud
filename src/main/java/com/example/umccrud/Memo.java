package com.example.umccrud;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

/* · @Entity

  → 테이블과 링크될 클래스임을 나타냅니다.

  → 기본값으로 클래스의 카멜케이스 이름을 언더스코어 네이밍(_)으로 테이블 이름을 매칭합니다.

  → ex) SalesManager → SALES_MANAGER 테이블

 · @Id

  → 해당 테이블의 PK 필드를 나타냅니다.

 · @GeneratedValue

  → PK 생성 규칙을 나타냅니다.

  → 스프링 부트 2.0에서는 GenerationType.IDENTITY 옵션을 추가해야만 auto_increment가 됨

 · @Column

  → 테이블의 컬럼을 나타내며 선언하지 않더라도 해당 클래스의 필드는 모두 컬럼이 됩니다.

  → 사용하는 이유는, 기본값 외에 추가로 변경이 필요한 옵션이 있을 경우 사용합니다.

  → ex) 기본 문자열의 사이즈는 VARCHAR(255)인데, 사이즈를 늘리거나, 타입을 TEXT로 변경할 때 사용됩니다.
  */
@Getter
@Setter
@Entity

public class Memo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

}

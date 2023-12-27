package com.example.umccrud;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemoRepository extends JpaRepository<Memo, Long> {
//    List<Memo> findByTitleContaining(String keyword);
//
//    List<Memo> findByTagsContaining(String tag);

    Page<Memo> findByTitleContainingOrderByCreatedAtDesc(String keyword, Pageable pageable);
    //findBy: 해당 메서드는 특정 엔티티를 검색하는 메서드임을 나타냄.
    //TitleContaining: Title 속성이 주어진 키워드를 포함하는 엔티티를 검색함. 해당 엔티티의 필드명에 따라 유연하게 적용된다
    //OrderByCreatedAtDesc: 검색 결과를 CreatedAt 속성을 기준으로 내림차순으로 정렬
    //Pageable pageable: 페이징 및 정렬을 지원하기 위해 Pageable 객체를 전달받아 결과를 페이징 처리합니다.

    Page<Memo> findByTitleContainingOrderByCreatedAtAsc(String keyword, Pageable pageable);



    Page<Memo> findAll( Pageable pageable);


    //여기서 T는 엔티티 클래스를 나타냅니다. 즉, findAll은 해당 엔티티의 모든 레코드를 반환하는 메서드.
    //예를 들어, Memo 엔티티에 대한 findAll 메서드는 다음과 같이 사용될 수 있다.
    //Spring Data JPA는 유연한 반환 타입을 지원한다.
    //기본적으로 결과가 한건 이상이면 컬렉션 인터페이스를 사용하고, 단건이면 반환 타입을 지정한다.
    //findall은 모든 레코드를 메모리에 한 번에 로드하는 방식이기 때문에 주의해야 한다.
    // 만약 데이터가 많다면 페이징이나 다른 방법을 고려해야 할 수 있다.
    //Spring Data JPA는 메서드 이름을 분석하여 기본적인 쿼리를 생성하므로, findAll 메서드의 경우에는 단순히 모든 레코드를 가져오는 용도로 사용된다.
    // 만약 특정 조건에 따라 데이터를 필터링하고 싶다면, 메서드 이름에 조건에 관련된 키워드를 추가하여 적절한 쿼리를 생성할 수 있다.
}

package study.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.datajpa.domain.Member;
import study.datajpa.domain.dto.MemberDto;

import javax.persistence.QueryHint;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    /**
     * < 메서드 이름으로 쿼리 생성 >
     * 파라미터가 많아지면 지저분해진다
     * 간단한 쿼리에 사용
     */
    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    /**
     * < JPA NamedQuery >
     * @Query 생략 가능 : 엔티티에 NamedQuery 먼저 찾고 없으면 메서드 이름으로 쿼리 생성
     */
    @Query(name = "Member.findByUsername")
    List<Member> findByUsername(@Param("username") String username);

    /**
     * < @Query, 리포지토리 메서드에 쿼리 정의하기 >
     * '이름 없는 NamedQuery' -> 애플리케이션 로딩 시점에 문법 오류를 발견
     */
    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    /**
     * < @Query, 값 타입, DTO 조회하기 >
     */
    @Query("select m.username from Member m")
    List<String> findUsernameList();

    @Query("select new study.datajpa.domain.dto.MemberDto(m.id, m.username, t.name)" +
            " from Member m join m.team t")
    List<MemberDto> findMemberDto();

    /**
     * < 페이징, 정렬 >
     * 파라미터로 Pageable 인터페이스 구현체 (PageRequest)를 넘김
     * 반환 타입
     1. Page : 추가 count 쿼리 포함
     2. Slice : 추가 count 쿼리 없이 다음 페이지만 확인 가능, 내부적으로 limit + 1 쿼리를 날림
     3. List : 추가 count 쿼리 없이 결과만 반환
     */
    Page<Member> findByAge(int age, Pageable pageable);

    @Query(value = "select m from Member m join m.team t",
            countQuery = "select count(m) from Member m")
        // count 쿼리 최적화
    Page<Member> findMemberAllCountBy(Pageable pageable);

    /**
     * < 벌크성 수정 쿼리 >
     * @Modifying 사용, 영속성 컨텍스트 초기화 (clearAutomatically = true)
     */
    @Modifying(clearAutomatically = true)
    @Query(value = "update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    /**
     * < @EntityGraph >
     * 페치 조인의 간편 버전
     * 지연 로딩으로 발생할 수 있는 쿼리 N + 1 문제를 해결하기 위해 페치 조인을 통해 엔티티 그래프 기능(연관된 엔티티 한번에 조회)을 사용
     * @EntityGraph(attributePaths = {""}) : JPQL 없이 페치 조인 사용 가능
     * NamedEntityGraph 도 가능
     */
    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin();

    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();

    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member m")
    List<Member> findMembersByEntityGraph();

    @EntityGraph(attributePaths = {"team"})
    List<Member> findByUsernameAndAgeLessThan(@Param("username") String username, @Param("age") int age);

    /**
     * < JPA Hint >
     * JPA 구현체(하이버네이트)에게 주는 힌트
     * ex) 오로지 조회용(ReadOnly)으로 최적화 하기 위해 사용 (변경 감지를 위한 스냅샷 객체 생성 X)
     */
    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Member findReadOnlyByUsername(String username);
}

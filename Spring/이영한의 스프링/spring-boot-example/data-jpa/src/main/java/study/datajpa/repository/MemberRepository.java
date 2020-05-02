package study.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.datajpa.domain.Member;
import study.datajpa.domain.dto.MemberDto;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    /**
     * < 메서드 이름으로 쿼리 생성 >
     * 파라미터가 많아지면 지저분해진다
     * 간단한 쿼리에 사용
     */
    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    /**
     * < JPA NamedQuery >
     *
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
     * 1. Page : 추가 count 쿼리 포함
     * 2. Slice : 추가 count 쿼리 없이 다음 페이지만 확인 가능, 내부적으로 limit + 1 쿼리를 날림
     * 3. List : 추가 count 쿼리 없이 결과만 반환
     */
    Page<Member> findByAge(int age, Pageable pageable);

    @Query(value = "select m from Member m join m.team t",
            countQuery = "select count(m) from Member m")    // count 쿼리 최적화
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
     */
}

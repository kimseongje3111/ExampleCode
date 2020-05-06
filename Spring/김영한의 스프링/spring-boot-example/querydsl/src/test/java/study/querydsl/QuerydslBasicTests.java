package study.querydsl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.domain.Member;
import study.querydsl.domain.QMember;
import study.querydsl.domain.QTeam;
import study.querydsl.domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.List;

import static com.querydsl.jpa.JPAExpressions.*;
import static org.assertj.core.api.Assertions.*;
import static study.querydsl.domain.QMember.*;
import static study.querydsl.domain.QTeam.*;

@SpringBootTest
@Transactional
public class QuerydslBasicTests {

    @Autowired
    EntityManager em;

    @Autowired
    EntityManagerFactory emf;

    JPAQueryFactory queryFactory;

    @BeforeEach
    public void before() {
        queryFactory = new JPAQueryFactory(em);

        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");

        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);
    }

    @Test
    public void startJPQL() {
        Member findMember = em.createQuery("select m from Member m where m.username =:username", Member.class)
                .setParameter("username", "member1")
                .getSingleResult();

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    public void startQuerydsl() {
        Member findMember = queryFactory
                .select(member)
                .from(member)
                .where(member.username.eq("member1"))
                .fetchOne();

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    public void search() {
        Member findMember = queryFactory
                .selectFrom(QMember.member)
                .where(QMember.member.username.eq("member1")
                        .and(QMember.member.age.eq(10)))
                .fetchOne();

        assertThat(findMember.getUsername()).isEqualTo("member1");
        assertThat(findMember.getAge()).isEqualTo(10);
    }

    @Test
    public void searchAndParam() {
        List<Member> findMembers = queryFactory
                .selectFrom(member)
                .where(
                        member.username.eq("member1"),
                        member.age.eq(10)       // null 값 무시 -> 동적 쿼리 활용
                )
                .fetch();

        assertThat(findMembers.size()).isEqualTo(1);
    }

    /**
     * 회원 정렬 순서
     * 1. 회원 나이 내림차순 (desc)
     * 2. 회원 이름 올림차순 (asc)
     * 단, 회원 이름이 없으면 마지막에 출력 (nulls last)
     */
    @Test
    public void sort() {
        em.persist(new Member(null, 100));
        em.persist(new Member("member5", 100));
        em.persist(new Member("member6", 100));

        List<Member> fetch = queryFactory
                .selectFrom(member)
                .where(member.age.eq(100))
                .orderBy(
                        member.age.desc(),
                        member.username.asc().nullsLast()
                )
                .fetch();

        Member member5 = fetch.get(0);
        Member member6 = fetch.get(1);
        Member memberNull = fetch.get(2);

        assertThat(member5.getUsername()).isEqualTo("member5");
        assertThat(member6.getUsername()).isEqualTo("member6");
        assertThat(memberNull.getUsername()).isNull();
    }

    @Test
    public void paging1() {
        List<Member> fetch = queryFactory
                .selectFrom(member)
                .orderBy(member.username.desc())
                .offset(1)
                .limit(2)
                .fetch();

        assertThat(fetch.size()).isEqualTo(2);
    }

    @Test
    public void paging2() {
        QueryResults<Member> fetch = queryFactory
                .selectFrom(member)
                .orderBy(member.username.desc())
                .offset(1)
                .limit(2)
                .fetchResults();

        assertThat(fetch.getTotal()).isEqualTo(4);
        assertThat(fetch.getLimit()).isEqualTo(2);
        assertThat(fetch.getOffset()).isEqualTo(1);
        assertThat(fetch.getResults().size()).isEqualTo(2);
    }

    @Test
    public void aggregation() {
        List<Tuple> fetch = queryFactory
                .select(
                        member.count(),
                        member.age.sum()
                )
                .from(member)
                .fetch();

        Tuple tuple = fetch.get(0);

        assertThat(tuple.get(member.count())).isEqualTo(4);
        assertThat(tuple.get(member.age.sum())).isEqualTo(100);
    }

    /**
     * 팀의 이름과 각 팀의 평균 연령
     */
    @Test
    public void group() throws Exception {
        // Given
        List<Tuple> fetch = queryFactory
                .select(
                        team.name,
                        member.age.avg()
                )
                .from(member)
                .join(member.team, team)
                .groupBy(team.name)
                .fetch();

        // When
        Tuple teamA = fetch.get(0);
        Tuple teamB = fetch.get(1);

        // Then
        assertThat(teamA.get(team.name)).isEqualTo("teamA");
        assertThat(teamA.get(member.age.avg())).isEqualTo(15);
        assertThat(teamB.get(team.name)).isEqualTo("teamB");
        assertThat(teamB.get(member.age.avg())).isEqualTo(35);
    }

    /**
     * 팀 A 에 소속된 모든 회원
     */
    @Test
    public void join() throws Exception {
        // Given
        List<Member> fetch = queryFactory
                .selectFrom(member)
                .join(member.team, team)
                .where(team.name.eq("teamA"))
                .fetch();

        // Then
        assertThat(fetch)
                .extracting("username")
                .containsExactly("member1", "member2");
    }

    /**
     * 연관 엔티티 외부 조인 필터링
     * 회원과 팀을 조인하면서, 팀 이름이 teamA 인 팀만 조인, 회원은 모두 조회
     */
    @Test
    public void join_on_filtering() throws Exception {
        // Given
        List<Tuple> fetch = queryFactory
                .select(
                        member,
                        team
                )
                .from(member)
                .leftJoin(member.team, team)
                .on(team.name.eq("teamA"))
                .fetch();
    }

    /**
     * 연관 관계가 없는 엔티티 외부 조인
     * 회원 이름과 팀 이름이 같은 대상 외부 조인
     */
    @Test
    public void join_on_no_relation() throws Exception {
        // Given
        em.persist(new Member("teamA"));
        em.persist(new Member("teamB"));

        List<Tuple> fetch = queryFactory
                .select(
                        member,
                        team
                )
                .from(member)
                .leftJoin(team)
                .on(member.username.eq(team.name))
                .fetch();
    }

    /**
     * 페치 조인
     */
    @Test
    public void fetch_join() throws Exception {
        // Given
        em.flush();
        em.clear();

        Member findMember = queryFactory
                .selectFrom(QMember.member)
                .join(QMember.member.team, team)
                .fetchJoin()
                .where(QMember.member.username.eq("member1"))
                .fetchOne();

        // When
        boolean loaded = emf.getPersistenceUnitUtil().isLoaded(findMember.getTeam());

        // Then
        assertThat(loaded).isTrue();
    }

    /**
     * 서브 쿼리
     * 나이가 가장 많은 회원 조회
     */
    @Test
    public void sub_query() throws Exception {
        // Given
        QMember memberSub = new QMember("memberSub");

        List<Member> fetch = queryFactory
                .selectFrom(member)
                .where(member.age.eq(
                        select(memberSub.age.max())
                                .from(memberSub)
                ))
                .fetch();

        // Then
        assertThat(fetch)
                .extracting("age")
                .containsExactly(40);
    }

    /**
     * 서브 쿼리
     * 나이가 10살 보다 큰 회원 조회
     */
    @Test
    public void sub_query_in() throws Exception {
        // Given
        QMember memberSub = new QMember("memberSub");

        List<Member> fetch = queryFactory
                .selectFrom(member)
                .where(member.age.in(
                        select(memberSub.age)
                                .from(memberSub)
                                .where(memberSub.age.gt(10))

                ))
                .fetch();

        // Then
        assertThat(fetch)
                .extracting("age")
                .containsExactly(20, 30, 40);
    }
}

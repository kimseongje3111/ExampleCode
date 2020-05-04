import jpa.jpql.domain.Member;
import jpa.jpql.domain.Team;
import jpa.jpql.domain.dto.MemberDTO;
import jpa.jpql.domain.valuetype.Address;

import javax.persistence.*;
import java.util.List;

public class JPQLMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            Team team = new Team();
            team.setName("teamA");

            em.persist(team);

            Member member = new Member();
            member.setUserName("memberA");
            member.setAge(20);
            member.changeTeam(team);

            em.persist(member);

            // < 반환 타입이 명확한가 > //
            TypedQuery<Member> typedQuery = em.createQuery("select m from Member m", Member.class);
            Query query = em.createQuery("select m.userName, m.age from Member m where m.id = 10");

            // < 결과 조회 > //
            List<Member> resultList = typedQuery.getResultList();
            Object[] result = (Object[]) query.getSingleResult();    // 정확히 하나가 아닌 경우 : 예외 발생

            // < 파라미터 바인딩 > //
            // 이름/위치 기준 (이름으로 쓰라)
            List<Member> parmBindingResultList = em.createQuery("select m from Member m where m.userName = :username", Member.class)
                    .setParameter("username", "memberA")
                    .getResultList();

            // < 프로젝션 > //
            // 조회 대상 지정 : 엔티티, 임베디드 타입, 스칼라 타입 (모두 영속성 컨텍스트가 관리)
            TypedQuery<Team> query1 = em.createQuery("select t from Member m join m.team t", Team.class);
            TypedQuery<Address> query2 = em.createQuery("select o.address from Order o", Address.class);
            /*
                * 여러 값을 조회하는 경우
                1. Query 타입으로 조회
                2. Object[] 타입으로 조회
                3. new 명령어로 조회 : DTO 활용, 패키지 경로를 명시하여 생성자 호출
             */
            List<MemberDTO> dtoResultList = em.createQuery("select distinct new jpa.jpql.domain.dto.MemberDTO(m.userName, m.age) from Member m", MemberDTO.class)
                    .getResultList();

            // < 페이징 > //
            List<Member> pagingResultList = em.createQuery("select m from Member m order by m.userName desc ", Member.class)
                    .setFirstResult(10)
                    .setMaxResults(20)
                    .getResultList();

            // < 조인 > //
            /*
                * ON절 가능 (JPA 2.1 이상)
                1. 내부 조인 : (inner) join
                2. 외부 조인 : left (outer) join
                3. 세타 조인 : from Member m, Team t where ~
             */
            List<Member> joinResultList = em.createQuery("select m from Member m join m.team t where t.name = :teamName", Member.class)
                    .setParameter("teamName", "teamA")
                    .getResultList();

            // < 서브 쿼리 > //
            // from 절의 서브 쿼리는 불가능 : 조인으로 해결하지 못한다면 네이티브 SQL 또는 애플리케이션에서 해결
            em.createQuery("select m from Member m where exists (select t from m.team t where t.name = 'teamA')");

            // < 조건식 > //
            // CASE 문
            String conditionQuery = "select case " +
                    "when m.age <= 10 then '학생 요금' " +
                    "when m.age >= 60 then '경로 요금' " +
                    "else '일반 요금' " +
                    "end " +
                    "from Member m";
            String conditionQuery2 = "select coalesce(m.userName, '이름이 없는 회원') as username from Member m";  // null 이 아니면 반환
            String conditionQuery3 = "select nullif(m.userName, '관리자') as username from Member m";             // 두 값이 같으면 null, 다르면 첫 번째 값 반환

            em.createQuery(conditionQuery);
            em.createQuery(conditionQuery2);
            em.createQuery(conditionQuery3);

            // < JPQL 함수 > //
            /*
                1. JPQL 표준 함수 : DB에 관계없이 사용
                2. 사용자 정의 함수 : DB 방언에 추가하고 function()으로 호출 (DB 방언마다 해당 DB에 종속적인 함수들이 대부분 등록되어 있음)
             */
            String functionQuery = "select function('group_concat', m.userName) from Member m";
            em.createQuery(functionQuery);

            tx.commit();

        } catch (Exception e) {
            tx.rollback();

        } finally {
            em.close();
        }

        em = emf.createEntityManager();
        tx.begin();

        try {
            Team teamA = new Team();
            teamA.setName("teamA");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("teamB");
            em.persist(teamB);

            Member memberA = new Member();
            memberA.setUserName("memberA");
            memberA.setTeam(teamA);
            em.persist(memberA);

            Member memberB = new Member();
            memberB.setUserName("memberA");
            memberB.setTeam(teamA);
            em.persist(memberB);

            Member memberC = new Member();
            memberC.setUserName("memberA");
            memberC.setTeam(teamB);
            em.persist(memberC);

            em.flush();
            em.clear();

            String query = "select m from Member m";
            List<Member> resultList = em.createQuery(query, Member.class).getResultList();

            for (Member member : resultList) {
                System.out.println("member = " + member.getUserName() + ", " + member.getTeam().getName());
                /*
                    * 지연 로딩
                    * 회원 1 : 팀 A (SQL)
                    * 회원 2 : 팀 A (1차 캐시)
                    * 회원 3 : 팀 B (SQL)
                    * 회원이 100명이면 ? : N + 1 문제 (N개의 추가 쿼리)
                 */
            }

            // < 페치(fetch) 조인 > //
            // 쿼리 한번으로 해결
            String fetchQuery = "select m from Member m join fetch m.team";
            List<Member> fetchResultList = em.createQuery(fetchQuery, Member.class).getResultList();

            for (Member member : fetchResultList) {
                System.out.println("member = " + member.getUserName() + ", " + member.getTeam().getName());
                // 페치 조인으로 회원과 팀을 함께 조회했기 때문에 지연 로딩 X
            }

            // < 컬렉션 페치(fetch) 조인 > //
            // 일대다 관계
            String fetchQuery2 = "select t from Team t join fetch t.members";
            List<Team> fetchResultList2 = em.createQuery(fetchQuery2, Team.class).getResultList();

            for (Team team : fetchResultList2) {
                System.out.println("team = " + team.getName());

                for (Member member : team.getMembers()) {
                    System.out.println("-> member = " + member.getUserName());
                }
            }

            // 컬렉션 페치 조인으로 인한 추가 데이터 중복 제거
            /*
                1. SQL 쿼리 : 하지만 전체 컬럼이 중복되어야 함 -> 실패
                2. 추가로 애플리케이션에서 중복 제거 시도
             */
            String fetchQuery3 = "select distinct t from Team t join fetch t.members";
            List<Team> fetchResultList3 = em.createQuery(fetchQuery3, Team.class).getResultList();

            for (Team team : fetchResultList2) {
                System.out.println("team = " + team.getName());

                for (Member member : team.getMembers()) {
                    System.out.println("-> member = " + member.getUserName());
                }
            }

            /*
                * 한계
                * 페치 조인 대상에게 별칭 X
                * 둘 이상의 컬렉션을 페치 조인 X
                * 컬렉션 페치 조인시 페이징 불가 (일대다) : 쿼리를 다대일로 뒤집거나 @BatchSize 설정을 통해 최적화
             */

            // < Named 쿼리 > //
            /*
                * 정적 쿼리
                * @NamedQuery 어노테이션 또는 xml 파일로 관리
                * 애플리케이션 로딩 시점에 SQL로 파싱되어 캐시 유지 및 검증
             */
            List<Member> namedResultList = em.createQuery("Member.findByUsername", Member.class)
                    .setParameter("username", "memberA")
                    .getResultList();

            // < 벌크 연산 > //
            /*
                * 변경 감지에 의한 update 또는 delete 는 N건의 단일 쿼리
                * 쿼리 한번으로 여러 테이블 로우를 변경
                * executeUpdate() : int
                * 영속성 컨텍스트를 무시하고 DB에 직접 쿼리 : 벌크 연산 수행을 먼저 수행하거나 벌크 연산 수행 후 영속성 컨텍스트를 초기화
             */
            int resultCount = em.createQuery("update Member m set m.age = 20").executeUpdate();
            em.clear();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();

        } finally {
            em.close();
        }

        emf.close();
    }
}

package study.datajpa.repository;

import study.datajpa.domain.Member;

import java.util.List;

// 사용자 정의 레퍼지토리
public interface MemberRepositoryCustom {

    List<Member> findMemberCustom();
}

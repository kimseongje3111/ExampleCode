package study.datajpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.datajpa.domain.Member;
import study.datajpa.domain.dto.MemberDto;
import study.datajpa.repository.MemberRepository;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    /**
     * 웹 확장 : 도메인 클래스 컨버터
     */
    @GetMapping(value = "/members/{id}")
    public String findMember(@PathVariable("id") Member member) {
        return member.getUsername();
    }

    /**
     * 웹 확장 : 페이징과 정렬
     * 실제 요청 파라미터에 따른 PageRequest 구현체 생성
     * page, size, sort
     * 스프링 부트 글로벌 설정, 개별 설정 (@PageableDefault)
     */
    @GetMapping(value = "/members")
    public Page<MemberDto> list(Pageable pageable) {
        return memberRepository.findAll(pageable).map(MemberDto::new);
    }
}

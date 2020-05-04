package jpabook.japshop.api;

import jpabook.japshop.domain.Member;
import jpabook.japshop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    /**
     * 회원 등록 V1
     * 요청 값으로 Member 엔티티를 직접 받는다
     * Json 형태로 온 Body 를 Member 객체로 매핑한다
     */
    @PostMapping(value = "/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        /*
         * < 단점 >
         * 엔티티에 화면 또는 API 검증을 위한 로직이 추가된다
         * 엔티티 변경시 API 스펙이 변경되는 결과를 초래한다
         */
        Long id = memberService.join(member);

        return new CreateMemberResponse(id);
    }

    /**
     * 회원 등록 V2
     * 엔티티 대신 DTD 를 RequestBody 에 매핑한다
     * V1 의 단점을 보완한 버전
     * 엔티티를 API 스펙에 노출하지 말자
     */
    @PostMapping(value = "/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {
        Member member = new Member();
        member.setName(request.getName());

        Long id = memberService.join(member);

        return new CreateMemberResponse(id);
    }

    /**
     * 회원 이름 수정
     * PUT : 같은 요청은 결과가 같다
     */
    @PutMapping(value = "/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id,
                                               @RequestBody @Valid UpdateMemberRequest request) {

        // 커맨드와 쿼리를 분리한다
        memberService.update(id, request.getName());
        Member findMember = memberService.findOne(id);

        return new UpdateMemberResponse(id, findMember.getName());
    }

    /**
     * 회원 조회 V1
     * 응답 값으로 엔티티를 직접 외부에 노출한다
     * 회원 등록 V1 과 동일한 문제
     * 화면에 종속적이다 : 해당 엔티티를 필요로하는 API 가 이것만 있는게 아니기 때문에 최악의 단점이다
     * 컬렉션을 직접 반환하면 향후 API 스펙을 변경하기 어렵다
     */
    @GetMapping(value = "/api/v1/members")
    public List<Member> membersV1() {
        return memberService.findMembers();
    }

    /**
     * 회원 조회 V2
     * 응답 값으로 엔티티가 아닌 별도의 DTO 사용
     * 엔티티가 변해도 API 스펙이 변경되지 않는다
     * Result 클래스(껍데기)로 컬렉션을 감싸서 향후 필요한 필드를 유연하게 추가할 수 있다
     */
    @GetMapping(value = "/api/v2/members")
    public Result memberV2() {
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m.getName()))
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @Data
    static class CreateMemberResponse {
        private Long id;

        CreateMemberResponse(Long id) {
            this.id = id;
        }
    }

    @Data
    static class CreateMemberRequest {
        // 별도의 DTO 를 통해 엔티티와 화면, 엔티티와 API 스펙을 분리할 수 있다
        @NotEmpty
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }

    @Data
    static class UpdateMemberRequest {
        @NotEmpty
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
        // 필요한 필드를 유연하게 추가할 수 있다
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String name;
    }
}

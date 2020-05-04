package jpabook.japshop.controller;

import jpabook.japshop.domain.Address;
import jpabook.japshop.domain.Member;
import jpabook.japshop.service.MemberService;
import lombok.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.persistence.Embedded;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping(value = "/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());

        return "members/createMemberForm";
    }

    @PostMapping(value = "/members/new")
    public String create(@Valid MemberForm form, BindingResult result) {
        if (result.hasErrors()) return "members/createMemberForm";

        Member member = new Member();
        Address address = new Address(form.getCity(), form.getStreet(), form.getZipCode());

        member.setName(form.getName());
        member.setAddress(address);

        try {
            memberService.join(member);

        } catch (IllegalStateException e) {
            result.addError(new FieldError("memberForm", "name", e.getMessage()));
            return "members/createMemberForm";
        }

        return "redirect:/";
    }

    @GetMapping(value = "/members")
    public String list(Model model) {
        // 엔티티를 직접 사용하기보다 DTO 또는 폼 객체를 사용하여 화면에 전달하자. 특히 API 를 만들때는 엔티티를 외부로 리턴하면 안된다.
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> members = findMembers.stream().map(m -> new MemberDto(m.getId(), m.getName(), m.getAddress()))
                .collect(Collectors.toList());

        model.addAttribute("members", members);

        return "members/memberList";
    }

    @Data
    static class MemberForm {
        @NotEmpty(message = "회원 이름은 필수입니다")
        private String name;

        private String city;
        private String street;
        private String zipCode;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private Long id;
        private String name;

        @Embedded
        private Address address;
    }
}

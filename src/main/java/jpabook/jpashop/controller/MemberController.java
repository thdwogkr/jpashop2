package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) {
        //@Valid 다음에 BindingResult 가 있으면 result 에 오류가 담기고 실행 됨
        //BindingResult 없었으면 메서드 실행 안되고 튕김

        //에러날때 whitelabel로 가는게 아니라 해당 페이지에서 에러 난걸 표시해줌
        //에러종류, 에러메시지는 MemberForm 클래스에서 확인 가능
        //라이브러리명 타임리프+스프링 기능이니까 공홈 Docs 참고하자
        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";    //다시 홈으로
    }

    @GetMapping("/members")
    public String list(Model model) {
        //여기선 엔티티를 보냈지만 웬만하면 DTO 보내라
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}

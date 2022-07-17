package argonet.board.controller;

import argonet.board.dto.BoardResponse;
import argonet.board.dto.MemberRequest;
import argonet.board.dto.MemberResponse;
import argonet.board.entity.Member;
import argonet.board.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/join")
    public String register() {
        return "join";
    }

    @PostMapping("/doRegister")
    public String doRegister(HttpServletRequest request) {
        MemberRequest member = new MemberRequest(
                request.getParameter("account"),
                request.getParameter("name"),
                request.getParameter("password"),
                request.getParameter("email")
        );
        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "/";
    }

    @GetMapping("/members")
    public String members(Model model) {
        List<MemberResponse> members = memberService.findAll();
        model.addAttribute("members");
        return "members";
    }

    @GetMapping("/member/info")
    public String getMemberInfo(@AuthenticationPrincipal Member member, Model model) {
        model.addAttribute("member", member);
        return "memberinfo";
    }


    @GetMapping("/member/find")
    public String findMemberByName() {
        return "search";
    }

    @PostMapping("/member/modify")
    public String updateMember(@AuthenticationPrincipal Member member, MemberRequest request) throws Exception {
        request.setId(member.getId());
        memberService.update(request);
        member.update(request.getName(), request.getEmail());
        return "redirect:/member/info";
    }

    @GetMapping("/member/delete")
    public String removeMember() {
        return "redirect:/";
    }
}

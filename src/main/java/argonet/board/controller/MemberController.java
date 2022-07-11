package argonet.board.controller;

import argonet.board.dto.MemberRequest;
import argonet.board.entity.Member;
import argonet.board.repository.MemberRepository;
import argonet.board.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/login")
    public String login(@RequestBody Member member) throws Exception {
        return memberService.login(member);
    }

    @PostMapping("/join")
    public void join(@RequestBody MemberRequest member) throws Exception {
        memberService.join(member);
    }

}

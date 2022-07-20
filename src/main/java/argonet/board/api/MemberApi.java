package argonet.board.api;

import argonet.board.dto.MemberRequest;
import argonet.board.dto.MemberResponse;
import argonet.board.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberApi {

    private final MemberService memberService;

    @GetMapping("/member/name")
    public List<MemberResponse> findByName(@RequestBody MemberRequest request) {
        return memberService.findByName(request.getName());
    }

    @GetMapping("/members")
    public List<MemberResponse> findAll() {
        return memberService.findAll();
    }

    @PostMapping("/login")
    public String login(@RequestBody MemberRequest member) throws Exception {
        return memberService.login(member);
    }

    @PostMapping("/join")
    public Long join(@RequestBody MemberRequest member) {
        return memberService.join(member);
    }

    @PatchMapping("/member")
    public void update(@RequestBody MemberRequest member) throws Exception {
        memberService.update(member);
    }

    @DeleteMapping("/member")
    public void delete(@RequestBody MemberRequest member) throws Exception {
        memberService.delete(member);
    }
}

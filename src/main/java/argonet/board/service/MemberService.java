package argonet.board.service;

import argonet.board.config.JwtGenerator;
import argonet.board.dto.MemberRequest;
import argonet.board.entity.Member;
import argonet.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    private final JwtGenerator jwtGenerator;

    // TODO: Add Duplicate Exception
    // TODO: Add Valid Token
    public Long join(MemberRequest request) throws Exception {
        Member member = new Member(request);
        memberRepository.save(member);
        return member.getId();
    }

    public String login(MemberRequest member) throws Exception{
        try {
            Member find = memberRepository.findByAccount(member.getAccount());
            if(find == null) {
                throw new Exception("존재하지 않는 회원입니다.");
            } else if(!find.matchPassword(member.getPassword())) {
                throw new Exception("알맞지 않은 비밀번호입니다.");
            } else {
                return jwtGenerator.createJwt(find);
            }
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

}

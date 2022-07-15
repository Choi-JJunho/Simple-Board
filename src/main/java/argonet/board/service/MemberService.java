package argonet.board.service;

import argonet.board.config.Login;
import argonet.board.dto.MemberResponse;
import argonet.board.util.JwtGenerator;
import argonet.board.dto.MemberRequest;
import argonet.board.entity.Member;
import argonet.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;

    private final JwtGenerator jwtGenerator;

    public Long join(MemberRequest request) {
        Member member = new Member(request);
        memberRepository.save(member);
        return member.getId();
    }

    public String login(MemberRequest member) throws Exception {
        try {
            Member find = memberRepository.findByAccount(member.getAccount());
            if (find == null) {
                throw new Exception("존재하지 않는 회원입니다.");
            } else if (!find.matchPassword(member.getPassword())) {
                throw new Exception("알맞지 않은 비밀번호입니다.");
            } else {
                return jwtGenerator.createJwt(find);
            }
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<MemberResponse> findAll() {
        return memberRepository.findAll().stream()
                .map(o -> new MemberResponse(o))
                .collect(Collectors.toList());
    }

    public List<MemberResponse> findByName(String name) {
        return memberRepository.findByName(name).stream()
                .map(o -> new MemberResponse(o))
                .collect(Collectors.toList());
    }


    public void update(MemberRequest request) throws Exception {
        Member member = memberRepository.findById(request.getId());
        if (member != null && member.matchPassword(request.getPassword())) {
            if (request.getPassword() == null) {
                member.update(request.getName(), request.getEmail(), member.getPassword());
            } else {
                member.update(request.getName(), request.getEmail(), request.getPassword());
            }
            memberRepository.save(member);
        } else {
            throw new Exception("유효하지 않은 사용자입니다.");
        }
    }



    public void delete(MemberRequest request) throws Exception {
        Member member = memberRepository.findById(request.getId());
        if (member != null && member.matchPassword(request.getPassword())) {
            member.deleteMember();
            memberRepository.save(member);
        } else {
            throw new Exception("유효하지 않은 사용자입니다.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        Member member = memberRepository.findByAccount(account);
        if(member == null) {
            throw new UsernameNotFoundException("유효하지 않은 사용자입니다");
        }
        return member;
    }
}

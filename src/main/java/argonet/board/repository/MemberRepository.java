package argonet.board.repository;

import argonet.board.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public Member findById(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("" +
                "select m from Member m", Member.class)
                .getResultList();
    }

    public Member findByAccount(String account) {
        return em.createQuery("select m from Member m" +
                        " where m.account = :account", Member.class)
                .setParameter("account", account)
                .getSingleResult();
    }

    public void save(Member member) {
        em.persist(member);
    }
}

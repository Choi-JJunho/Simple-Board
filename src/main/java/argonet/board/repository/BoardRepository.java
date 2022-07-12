package argonet.board.repository;

import argonet.board.entity.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepository {

    private final EntityManager em;

    public void save(Board board){
        em.persist(board);
    }

    public Board findById(Long id) {
        return em.find(Board.class, id);
    }

    public List<Board> findAll() {
        return em.createQuery("select distinct b from Board b " +
                        "join fetch b.member " +
                        "join fetch b.comments", Board.class)
                .getResultList();
    }

    public void remove(Board board) {
        em.remove(board);
    }

    public List<Board> findByMember(Long id) {
        return em.createQuery("select distinct b from Board b" +
                        " join fetch b.member " +
                        " join fetch b.comments" +
                        " where b.member.id = :id", Board.class)
                .setParameter("id", id)
                .getResultList();
    }
}

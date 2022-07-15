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
        Board board = em.find(Board.class, id);
        if (board.getIsDeleted()) {
            return null;
        } else {
            return board;
        }
    }

    public List<Board> findAll() {
        return em.createQuery("select distinct b from Board b " +
                        "where b.isDeleted = false " +
                        "order by b.id asc", Board.class)
                .getResultList();
    }

    public void remove(Board board) {
        em.remove(board);
    }

    public List<Board> findByMember(Long id) {
        return em.createQuery("select distinct b from Board b" +
                        " where b.isDeleted = false and b.member.id = :id", Board.class)
                .setParameter("id", id)
                .getResultList();
    }
}

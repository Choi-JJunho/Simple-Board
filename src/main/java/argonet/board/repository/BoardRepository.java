package argonet.board.repository;

import argonet.board.entity.Board;
import argonet.board.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
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

    public List<Board> findBySortRule(String sorter, int page) {
        int start = (page - 1) * 10;
        return em.createQuery("select b from Board b "+
                "where b.isDeleted = false " +
                "order by "+ sorter, Board.class)
                .setFirstResult(start)
                .setMaxResults(start + 10)
                .getResultList();
    }

    public void remove(Board board) {
        em.remove(board);
    }

    public List<Board> findByMember(Long id, String sorter, int page) {
        int start = (page - 1) * 10;
        return em.createQuery("select distinct b from Board b" +
                        " where b.isDeleted = false and b.member.id = :id" +
                        " order by "+ sorter, Board.class)
                .setParameter("id", id)
                .setFirstResult(start)
                .setMaxResults(start + 10)
                .getResultList();
    }

    public Long findLastIndex() {
        return em.createQuery("select b.id from Board b " +
                "order by b.id desc", Long.class).setMaxResults(1)
                .getSingleResult();
    }

    public List<Board> findByTitle(String title, int page, String sorter) {
        int start = (page - 1) * 10;
        return em.createQuery("select b from Board b" +
                        " where b.isDeleted = false and b.title like concat('%',:title,'%')" +
                        " order by " + sorter, Board.class)
                .setParameter("title", title)
                .setFirstResult(start)
                .setMaxResults(start + 10)
                .getResultList();
    }
    public List<Board> findByDescription(String description, int page, String sorter) {
        int start = (page - 1) * 10;
        return em.createQuery("select b from Board b" +
                        " where b.isDeleted = false and b.description like concat('%',:description,'%')" +
                        " order by " + sorter, Board.class)
                .setParameter("description", description)
                .setFirstResult(start)
                .setMaxResults(start + 10)
                .getResultList();
    }
    public List<Board> findByWriter(String writer, int page, String sorter) {
        int start = (page - 1) * 10;
        return em.createQuery("select b from Board b" +
                        " where b.isDeleted = false and b.member.name like concat('%',:writer,'%')" +
                        " order by " + sorter, Board.class)
                .setParameter("writer", writer)
                .setFirstResult(start)
                .setMaxResults(start + 10)
                .getResultList();
    }

}

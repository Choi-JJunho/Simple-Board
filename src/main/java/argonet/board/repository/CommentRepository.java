package argonet.board.repository;

import argonet.board.entity.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;

    public void save(Comment comment) {
        em.persist(comment);
    }

    public Comment findById(Long id) {
        Comment comment = em.find(Comment.class, id);
        if(comment.getIsDeleted()) {
            return null;
        } else {
            return comment;
        }
    }
}

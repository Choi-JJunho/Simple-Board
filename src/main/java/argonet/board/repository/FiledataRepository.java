package argonet.board.repository;

import argonet.board.entity.Filedata;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FiledataRepository {
    private final EntityManager em;

    public void save(Filedata file) {
        em.persist(file);
    }

    public Filedata findOne(Long id) {
        return em.find(Filedata.class, id);
    }

    public Filedata findByUUid(String id) {
        return em.createQuery("select f from Filedata f " +
                        "where f.uuid = :id", Filedata.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public List<Filedata> findByBoardId(Long id) {
        return em.createQuery("select f from Filedata f " +
                        "where f.board.id = :id", Filedata.class)
                .setParameter("id", id)
                .getResultList();
    }

    public void remove(Filedata file) {
        em.remove(file);
    }
}

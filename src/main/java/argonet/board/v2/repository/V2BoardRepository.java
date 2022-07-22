package argonet.board.v2.repository;

import argonet.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface V2BoardRepository extends JpaRepository<Board, Long> {

    Page<Board> findAllByIsDeletedFalse(Pageable pageable);
}

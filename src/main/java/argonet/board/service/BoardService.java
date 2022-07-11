package argonet.board.service;

import argonet.board.dto.BoardRequest;
import argonet.board.entity.Board;
import argonet.board.repository.BoardRepository;
import argonet.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;

    private final MemberRepository memberRepository;

    public void saveBoard(BoardRequest request) {
        Board board = new Board(memberRepository.findById(request.getMemberId()), request.getTitle(), request.getDescription());
        boardRepository.save(board);
    }

    public void remove(Long id) {
        Board board = boardRepository.findById(id);
        boardRepository.remove(board);
    }

    public void update(BoardRequest request) {
        Board board = boardRepository.findById(request.getId());
        board.modify(request.getTitle(), request.getDescription());
        boardRepository.save(board);
    }
}

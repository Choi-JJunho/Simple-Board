package argonet.board.service;

import argonet.board.config.Login;
import argonet.board.dto.BoardRequest;
import argonet.board.dto.BoardResponse;
import argonet.board.entity.Board;
import argonet.board.repository.BoardRepository;
import argonet.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    private final MemberRepository memberRepository;

    public BoardResponse findById(Long id) {
        return new BoardResponse(boardRepository.findById(id));
    }

    public List<BoardResponse> findAll() {
        List<Board> boards = boardRepository.findAll();
        return boards.stream()
                .map(o -> new BoardResponse(o))
                .collect(Collectors.toList());
    }


    @Transactional
    public void saveBoard(BoardRequest request) throws Exception {
        Board board = new Board(memberRepository.findById(request.getMemberId()), request.getTitle(), request.getDescription());
        if(board.getMember() == null) {
            throw new Exception("존재하지 않는 유저입니다.");
        }
        boardRepository.save(board);
    }

    // soft delete

    @Transactional
    public void remove(Long id) {
        Board board = boardRepository.findById(id);
        board.delete();
        boardRepository.save(board);
    }


    @Transactional
    public BoardResponse update(BoardRequest request) {
        Board board = boardRepository.findById(request.getId());
        board.modify(request.getTitle(), request.getDescription());
        boardRepository.save(board);
        return new BoardResponse(board);
    }

    public List<BoardResponse> findByMemberId(Long id) {
        List<Board> boards = boardRepository.findByMember(id);
        return boards.stream()
                .map(o -> new BoardResponse(o))
                .collect(Collectors.toList());
    }
}

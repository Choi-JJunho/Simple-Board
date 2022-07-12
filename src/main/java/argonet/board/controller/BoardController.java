package argonet.board.controller;

import argonet.board.dto.BoardRequest;
import argonet.board.dto.BoardResponse;
import argonet.board.dto.MemberRequest;
import argonet.board.entity.Board;
import argonet.board.repository.BoardRepository;
import argonet.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final BoardRepository boardRepository;
    @GetMapping("/board/{id}")
    public BoardResponse getBoardById(@PathVariable(value = "id") Long id) {
        return boardService.findById(id);
    }
    @GetMapping("/boards")
    public List<BoardResponse> getAllBoards() {
        return boardService.findAll();
    }

    @GetMapping("/member/boards")
    public List<BoardResponse> getMemberBoards(@RequestBody MemberRequest request) {
        return boardService.findByMemberId(request.getId());
    }

    @PostMapping("/board/post")
    public void postBoard(@RequestBody BoardRequest request) {
        boardService.saveBoard(request);
    }

    @DeleteMapping("/board/{id}")
    public void deleteBoard(@PathVariable(value = "id") Long id) {
        boardService.remove(id);
    }

    @PatchMapping("/board/modify")
    public void modifyBoard(@RequestBody BoardRequest request) {
        boardService.update(request);
    }

}

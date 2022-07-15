package argonet.board.api;

import argonet.board.dto.BoardRequest;
import argonet.board.dto.BoardResponse;
import argonet.board.dto.MemberRequest;
import argonet.board.repository.BoardRepository;
import argonet.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardApi {
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

    @GetMapping("/boards/member")
    public List<BoardResponse> getMemberBoards(@RequestBody MemberRequest request) {
        return boardService.findByMemberId(request.getId());
    }

    @PostMapping("/board/post")
    public void postBoard(@RequestBody BoardRequest request) throws Exception {
        boardService.saveBoard(request);
    }

    @DeleteMapping("/board")
    public String deleteBoard(@RequestBody BoardRequest request) {
        boardService.remove(request.getId());
        return "Board Deleted";
    }

    @PatchMapping("/board/modify")
    public BoardResponse modifyBoard(@RequestBody BoardRequest request) {
        return boardService.update(request);
    }

}

package argonet.board.controller;

import argonet.board.dto.BoardRequest;
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
    public Board getBoardById(@PathVariable(value = "id") Long id) {
        return boardRepository.findById(id);
    }
    @GetMapping("/boards")
    public List<Board> getBoards() {
        return boardRepository.findAll();
    }

    @PostMapping("/board/post")
    public void postBoard(BoardRequest request) {
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

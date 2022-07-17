package argonet.board.controller;

import argonet.board.dto.BoardRequest;
import argonet.board.dto.BoardResponse;
import argonet.board.entity.Member;
import argonet.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping({"/", "/home"})
    public String homeView(Model model, @AuthenticationPrincipal Member member) {
        List<BoardResponse> boards = boardService.findAll();
        model.addAttribute("member", member);
        boards = simpleDescription(boards);
        model.addAttribute("boards", boards);
        return "home";
    }

    private List<BoardResponse> simpleDescription(List<BoardResponse> boards) {
        for (BoardResponse board : boards) {
            board.simpleDescription();
        }
        return boards;
    }

    @GetMapping("/boards")
    public String getboards(Model model, @AuthenticationPrincipal Member member) {
        List<BoardResponse> boards = boardService.findAll();
        for (BoardResponse board : boards) {
            board.simpleDescription();
        }
        model.addAttribute("boards", boards);
        model.addAttribute("member", member);
        return "home";
    }

    @GetMapping("/boards/member")
    public String getBoardByMember(@AuthenticationPrincipal Member member, Model model) {
        List<BoardResponse> boards = boardService.findByMemberId(member.getId());
        for (BoardResponse board : boards) {
            board.simpleDescription();
        }
        model.addAttribute("boards", boards);
        model.addAttribute("member", member);
        return "home";
    }

    @GetMapping("/board/post")
    public String boardPostPage(@AuthenticationPrincipal Member member, Model model) throws Exception {
        model.addAttribute("member", member);
        return "post";
    }

    @PostMapping("/board/post")
    public String boardPost(@AuthenticationPrincipal Member member, BoardRequest request, Model model) throws Exception {
        model.addAttribute("member", member);
        request.setMemberId(member.getId());
        boardService.saveBoard(request);
        return "redirect:/boards";
    }

    @GetMapping("/board/{id}")
    public String getBoardById(@AuthenticationPrincipal Member member, @PathVariable(value = "id") Long id, Model model, HttpServletRequest request) {
        BoardResponse board = boardService.findById(id);
        model.addAttribute("board", board);
        model.addAttribute("member", member);
        return "board";
    }

    @GetMapping("/board/{id}/modify")
    public String boardModifyPage(@AuthenticationPrincipal Member member, @PathVariable Long id, Model model) {
        model.addAttribute("post_id", id);
        BoardResponse board = boardService.findById(id);
        model.addAttribute("board", board);
        model.addAttribute("member", member);
        return "post";
    }

    @PostMapping("/board/{id}/modify")
    public String boardModify(@AuthenticationPrincipal Member member, @PathVariable(value = "id") Long id, BoardRequest request, Model model) {
        boardService.update(request);
        return "redirect:/board/" + id;
    }

    @PostMapping("/board/{id}/delete")
    public String boardRemove(@AuthenticationPrincipal Member member, @PathVariable(value = "id") Long id, HttpServletRequest request) {
        if (member != null) boardService.remove(id);
        return "redirect:/";
    }

}

package argonet.board.controller;

import argonet.board.dto.BoardRequest;
import argonet.board.dto.BoardResponse;
import argonet.board.entity.Board;
import argonet.board.entity.Member;
import argonet.board.service.BoardService;
import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping({"/", "/home"})
    public String homeView(Model model, @AuthenticationPrincipal Member member) {
        List<BoardResponse> boards = boardService.findAll();
        model.addAttribute("member", member);
        model.addAttribute("boards", boards);
        return "home";
    }

    @GetMapping("/boards")
    public String getboards (Model model, @AuthenticationPrincipal Member member) {
        List<BoardResponse> boards = boardService.findAll();
        model.addAttribute("boards", boards);
        return "home";
    }

    @GetMapping("/boards/member")
    public String getBoardByMember(@AuthenticationPrincipal Member member, Model model) {
        List<BoardResponse> boards = boardService.findByMemberId(member.getId());
        model.addAttribute("boards", boards);
        return "home";
    }

    @PostMapping("/board/post")
    public String boardPost(@AuthenticationPrincipal Member member, BoardRequest request, Model model) throws Exception {
        model.addAttribute("member", member);
        request.setMemberId(member.getId());
        boardService.saveBoard(request);
        return "post";
    }

    @GetMapping("/board/{id}")
    public String getBoardById(@AuthenticationPrincipal Member member, @PathVariable(value = "id") Long id, Model model) {
        BoardResponse board = boardService.findById(id);
        model.addAttribute("board", board);
        model.addAttribute("member", member);
        return "board";
    }

    @PostMapping("/board/{id}/modify")
    public String boardModify(@AuthenticationPrincipal Member member,@PathVariable(value = "id") Long id, HttpServletRequest request, Model model) {
        BoardResponse board = boardService.findById(id);
        model.addAttribute("board", board);
        model.addAttribute("member", member);
        return "redirect:/boards";
    }

    @PostMapping("/board/{id}/delete")
    public String boardRemove(@AuthenticationPrincipal Member member, @PathVariable(value = "id") Long id, HttpServletRequest request) {
        if(member != null) boardService.remove(id);
        return "redirect:/";
    }

}

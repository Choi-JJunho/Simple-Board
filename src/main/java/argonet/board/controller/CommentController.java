package argonet.board.controller;

import argonet.board.dto.CommentRequest;
import argonet.board.entity.Member;
import argonet.board.repository.CommentRepository;
import argonet.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board/{id}")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment/post")
    public String createComment(@AuthenticationPrincipal Member member, @PathVariable(value = "id") Long id, CommentRequest request) throws Exception {
        request.setMemberId(member.getId());
        request.setBoardId(id);
        commentService.save(request);
        return "redirect:/board/" + id;
    }

    @PostMapping("/comment/{comment_id}/delete")
    public String deleteComment(@PathVariable(value = "comment_id") Long comment_id, @PathVariable(value = "id") Long id) throws Exception {
        commentService.remove(comment_id);
        return "redirect:/board/" + id;
    }

    @PostMapping("/comment/{comment_id}/modify")
    public String updateComment(@PathVariable(value = "comment_id") Long comment_id, @PathVariable(value = "id") Long id, HttpServletRequest request) throws Exception {
        CommentRequest comment = new CommentRequest();
        comment.setId(comment_id);
        comment.setDescription(request.getParameter("description"));
        commentService.update(comment);
        return "redirect:/board/" + id;
    }
}
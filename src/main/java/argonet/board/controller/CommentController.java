package argonet.board.controller;

import argonet.board.dto.CommentRequest;
import argonet.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/comment")
    public void postComment(@RequestBody CommentRequest comment) throws Exception {
        commentService.save(comment);
    }

    @PatchMapping("/comment")
    public void modifyComment(@RequestBody CommentRequest comment) {
        commentService.update(comment);
    }

    @DeleteMapping("/comment")
    public void removeComment(@RequestBody CommentRequest comment) {
        commentService.remove(comment.getId());
    }
}

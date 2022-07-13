package argonet.board.controller;

import argonet.board.dto.CommentRequest;
import argonet.board.dto.CommentResponse;
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
    public CommentResponse modifyComment(@RequestBody CommentRequest comment) {
        return commentService.update(comment);
    }

    @DeleteMapping("/comment")
    public String removeComment(@RequestBody CommentRequest comment) {
        commentService.remove(comment.getId());
        return "Comment Deleted";
    }
}

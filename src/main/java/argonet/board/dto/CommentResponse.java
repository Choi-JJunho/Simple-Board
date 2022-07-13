package argonet.board.dto;

import argonet.board.entity.Comment;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentResponse {
    private Long id;
    private String description;
    private LocalDateTime createdAt;
    private MemberResponse member;

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.description = comment.getDescription();
        this.createdAt = comment.getCreatedAt();
        this.member = new MemberResponse(comment.getMember());
    }
}

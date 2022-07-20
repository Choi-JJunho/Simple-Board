package argonet.board.dto;

import argonet.board.entity.Comment;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class CommentResponse {
    private Long id;
    private String description;
    private String createdAt;
    private String memberName;
    private Long memberId;

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.description = comment.getDescription();
        this.createdAt = comment.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm"));
        this.memberName = comment.getMember().getName();
        this.memberId = comment.getMember().getId();
    }
}

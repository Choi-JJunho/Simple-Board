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

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.description = comment.getDescription();
        this.createdAt = comment.getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE);
        this.memberName = comment.getMember().getName();
    }
}

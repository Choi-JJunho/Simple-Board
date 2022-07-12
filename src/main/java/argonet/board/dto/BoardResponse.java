package argonet.board.dto;

import argonet.board.entity.Board;
import argonet.board.entity.Comment;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class BoardResponse {
    private Long id;
    private Long memberId;
    private String title;
    private String description;
    private List<CommentResponse> comments;

    public BoardResponse(Board o) {
        this.id = o.getId();
        this.memberId = o.getMember().getId();
        this.title = o.getTitle();
        this.description = o.getDescription();
        this.comments = o.getComments().stream()
                .map(a -> new CommentResponse(a))
                .collect(Collectors.toList());
    }
}

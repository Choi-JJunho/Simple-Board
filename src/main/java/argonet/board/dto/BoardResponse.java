package argonet.board.dto;

import argonet.board.entity.Board;
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

    public BoardResponse(Board board) {
        this.id = board.getId();
        this.memberId = board.getMember().getId();
        this.title = board.getTitle();
        this.description = board.getDescription();
        this.comments = board.getComments().stream()
                .map(o -> new CommentResponse(o))
                .collect(Collectors.toList());
    }
}

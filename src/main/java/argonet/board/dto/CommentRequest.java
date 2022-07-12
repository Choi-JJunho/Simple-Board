package argonet.board.dto;

import lombok.Data;

@Data
public class CommentRequest {
    private Long id;
    private Long boardId;
    private Long memberId;
    private String description;
}

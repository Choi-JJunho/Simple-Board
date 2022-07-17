package argonet.board.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentRequest {
    private Long id;
    private Long boardId;
    private Long memberId;
    private String description;
}

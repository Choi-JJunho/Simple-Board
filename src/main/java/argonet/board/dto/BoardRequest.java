package argonet.board.dto;

import argonet.board.entity.Member;
import lombok.Data;

@Data
public class BoardRequest {
    private Long id;
    private Long memberId;
    private String title;
    private String description;
}

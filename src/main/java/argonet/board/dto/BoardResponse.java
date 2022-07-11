package argonet.board.dto;

import lombok.Data;

@Data
public class BoardResponse {
    private Long id;
    private Long memberId;
    private String title;
    private String description;
}

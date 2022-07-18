package argonet.board.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FiledataRequest {
    private Long id;
    private String uuid;
    private String name;
    private String type;
    private Long boardId;
    private Long memberId;

    public FiledataRequest(String uuid, String name, String type, Long boardId, Long memberId) {
        this.uuid = uuid;
        this.name = name;
        this.type = type;
        this.boardId = boardId;
        this.memberId = memberId;
    }
}

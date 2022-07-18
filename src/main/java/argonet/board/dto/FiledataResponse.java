package argonet.board.dto;

import argonet.board.entity.Comment;
import argonet.board.entity.Filedata;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
public class FiledataResponse {

    private Long id;
    private String uuid;
    private String name;
    private String type;
    private Long memberId;
    private Long boardId;;

    public FiledataResponse(Filedata filedata) {
        this.id = filedata.getId();
        this.uuid = filedata.getUuid();
        this.name = filedata.getName();
        this.type = filedata.getType();
        this.memberId = filedata.getMember().getId();
        this.boardId = filedata.getBoard().getId();
    }
}

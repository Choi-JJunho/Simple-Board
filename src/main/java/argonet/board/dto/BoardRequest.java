package argonet.board.dto;

import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class BoardRequest {

    private Long id;
    private Long memberId;
    private String title;
    private String description;
}

package argonet.board.dto;

import lombok.Data;

@Data
public class MemberResponse {
    private Long id;
    private String account;
    private String name;
    private String email;
}

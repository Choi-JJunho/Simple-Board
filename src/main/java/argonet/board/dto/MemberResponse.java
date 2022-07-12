package argonet.board.dto;

import argonet.board.entity.Member;
import lombok.Data;

@Data
public class MemberResponse {
    private Long id;
    private String name;
    private String email;

    public MemberResponse(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.email = member.getEmail();
    }
}

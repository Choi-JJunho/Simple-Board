package argonet.board.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class MemberRequest {
    @JsonIgnore
    private Long id;
    private String account;
    private String name;
    private String password;
    private String email;

    public MemberRequest(Long id, String account, String name, String password, String email) {
        this.id = id;
        this.account = account;
        this.name = name;
        this.password = password;
        this.email = email;
    }
}

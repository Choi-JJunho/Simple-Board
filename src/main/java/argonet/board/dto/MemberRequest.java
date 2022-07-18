package argonet.board.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@NoArgsConstructor
public class MemberRequest {

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

    public MemberRequest(String account, String name, String password, String email) {
        this.account = account;
        this.name = name;
        this.password = password;
        this.email = email;
    }
}

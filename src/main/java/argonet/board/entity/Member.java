package argonet.board.entity;

import argonet.board.dto.MemberRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    @JsonIgnore
    private Long id;

    @Column(unique = true)
    private String account;
    private String name;
    private String password;
    private String email;

    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
    private LocalDateTime isDeleted;

    public Boolean matchPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(password, this.password);
    }

    public Member(MemberRequest memberRequest) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.id = memberRequest.getId();
        this.name = memberRequest.getName();
        this.account = memberRequest.getAccount();
        this.password = passwordEncoder.encode(memberRequest.getPassword());
        this.email = memberRequest.getEmail();
    }

}

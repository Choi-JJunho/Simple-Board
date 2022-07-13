package argonet.board.entity;

import argonet.board.dto.MemberRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    @JsonIgnore
    private Long id;

    @Column(unique = true)
    private String account;
    private String name;
    private String password;
    @Email(message = "이메일 형식이 아닙니다.")
    private String email;

    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;

    @Column(nullable = false, insertable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isDeleted;

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

    public void update(String name, String email, String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.name = name;
        this.email = email;
        this.password = passwordEncoder.encode(password);
    }

    public void deleteMember() {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }

}

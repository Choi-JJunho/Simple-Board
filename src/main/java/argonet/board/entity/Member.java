package argonet.board.entity;

import argonet.board.dto.MemberRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Getter
@NoArgsConstructor
public class Member implements UserDetails {

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
        return passwordEncoder().matches(password, this.password);
    }

    public Member(MemberRequest memberRequest) {
        this.id = memberRequest.getId();
        this.name = memberRequest.getName();
        this.account = memberRequest.getAccount();
        this.password = passwordEncoder().encode(memberRequest.getPassword());
        this.email = memberRequest.getEmail();
    }

    public void update(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = passwordEncoder().encode(password);
    }

    public void deleteMember() {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.account;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Bean
    private PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}

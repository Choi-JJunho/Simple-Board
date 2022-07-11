package argonet.board.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Board {

    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String title;
    private String description;

    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
    private LocalDateTime isDeleted;

    public Board(Member member, String title, String description) {
        this.member = member;
        this.title = title;
        this.description = description;
    }

    public void modify(String title, String description) {
        this.title = title;
        this.description = description;
    }
}

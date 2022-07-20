package argonet.board.entity;

import argonet.board.dto.FiledataRequest;
import argonet.board.repository.MemberRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Filedata {
    @Id @GeneratedValue
    @Column(name = "file_id")
    private Long id;
    private String uuid;
    private String name;
    private String type;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @JsonIgnore
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    @JsonIgnore
    private Board board;

    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;

    public Filedata(String uuid, String name, String type, Long boardId) {
        this.uuid = uuid;
        this.name = name;
        this.type = type;
    }

    public Filedata(FiledataRequest request, Member member, Board board) {
        this.uuid = request.getUuid();
        this.name = request.getName();
        this.type = request.getType();
        this.member = member;
        this.board = board;
    }
}

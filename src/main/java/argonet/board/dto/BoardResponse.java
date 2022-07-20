package argonet.board.dto;

import argonet.board.entity.Board;
import lombok.Data;
import org.jsoup.Jsoup;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class BoardResponse {
    private Long id;
    private Long memberId;
    private String title;
    private String description;
    private List<CommentResponse> comments;

    private String memberName;
    private String createdAt;

    public BoardResponse(Board board) {
        this.id = board.getId();
        this.memberId = board.getMember().getId();
        this.title = board.getTitle();
        this.description = board.getDescription();
        this.comments = board.getComments().stream()
                .map(o -> new CommentResponse(o))
                .collect(Collectors.toList());
        this.memberName = board.getMember().getName();
        this.createdAt = board.getCreatedAt().format(DateTimeFormatter.ISO_DATE);
    }

    public void simpleDescription() {
        String desc = Jsoup.parse(this.description).text();
        String title = Jsoup.parse(this.title).text();
        if (desc.length() > 10) {
            this.description = (desc.substring(0, 10)) + "...";
        } else {
            this.description = desc;
        }
        if (title.length() > 10) {
            this.title = (title.substring(0, 10)) + "...";
        } else {
            this.title = title;
        }
    }
}

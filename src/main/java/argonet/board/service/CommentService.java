package argonet.board.service;


import argonet.board.dto.CommentRequest;
import argonet.board.entity.Board;
import argonet.board.entity.Comment;
import argonet.board.entity.Member;
import argonet.board.repository.BoardRepository;
import argonet.board.repository.CommentRepository;
import argonet.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    public void save(CommentRequest request) throws Exception{
        Member member = memberRepository.findById(request.getMemberId());
        Board board = boardRepository.findById(request.getBoardId());
        if(member == null || board == null) {
            throw new Exception("유저 혹은 게시글에 대한 잘못된 참조입니다.");
        }
        Comment comment = new Comment(request.getDescription(), member, board);
        commentRepository.save(comment);
    }

    // TODO : Valid member
    public void update(CommentRequest request) {
        Comment comment = commentRepository.findById(request.getId());
        comment.update(request.getDescription());
        commentRepository.save(comment);
    }

    public void remove(Long id) {
        Comment comment = commentRepository.findById(id);
        comment.delete();
        commentRepository.save(comment);
    }
}

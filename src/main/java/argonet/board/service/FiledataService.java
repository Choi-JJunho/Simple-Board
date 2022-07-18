package argonet.board.service;

import argonet.board.dto.FiledataRequest;
import argonet.board.dto.FiledataResponse;
import argonet.board.entity.Board;
import argonet.board.entity.Filedata;
import argonet.board.entity.Member;
import argonet.board.repository.BoardRepository;
import argonet.board.repository.FiledataRepository;
import argonet.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FiledataService {
    private final FiledataRepository fileDataRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;


    public void save(List<FiledataRequest> requests) {
        for (FiledataRequest file : requests) {
            Member member = memberRepository.findById(file.getMemberId());
            Board board = boardRepository.findById(file.getBoardId());
            fileDataRepository.save(new Filedata(file, member, board));
        }
    }

    public List<FiledataResponse> findByBoardId(Long id) {
        List<Filedata> files = fileDataRepository.findByBoardId(id);
        return files.stream().map(o -> new FiledataResponse(o))
                .collect(Collectors.toList());
    }

    public void remove(Long id, String path) {
        File target = new File(String.valueOf(path));
        System.out.println(String.valueOf(path));
        target.delete();
        Filedata file = fileDataRepository.findOne(id);
        fileDataRepository.remove(file);
    }
}

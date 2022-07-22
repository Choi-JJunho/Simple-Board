package argonet.board.v2.service;

import argonet.board.v2.repository.V2BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class V2BoardService {

    private final V2BoardRepository boardRepository;

}

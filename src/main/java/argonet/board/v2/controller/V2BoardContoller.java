package argonet.board.v2.controller;

import argonet.board.entity.Board;
import argonet.board.v2.repository.V2BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/v2")
@RequiredArgsConstructor
public class V2BoardContoller {

    private final V2BoardRepository boardRepository;

    @ResponseBody
    @GetMapping("/home")
    public Page<Board> main(Pageable pageable) {
        PageRequest request = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("title"));
        Page<Board> page = boardRepository.findAllByIsDeletedFalse(request);
        System.out.println(page);
        return page;
    }
}

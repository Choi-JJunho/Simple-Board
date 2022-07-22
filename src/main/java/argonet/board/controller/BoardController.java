package argonet.board.controller;

import argonet.board.dto.BoardRequest;
import argonet.board.dto.BoardResponse;
import argonet.board.dto.FiledataRequest;
import argonet.board.dto.FiledataResponse;
import argonet.board.entity.Member;
import argonet.board.service.BoardService;
import argonet.board.service.FiledataService;
import argonet.board.v2.repository.V2BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final FiledataService filedataService;
    private final V2BoardRepository v2BoardRepository;

    @Value("${spring.servlet.multipart.location}")
    String filePath;

    @GetMapping("/paging")
    public String paging(Pageable pageable) {
        return "home";
    }

    @GetMapping({"/", "/home"})
    public String homeView(Model model, @AuthenticationPrincipal Member member,
                           HttpServletRequest request,
                           @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                           @RequestParam(value = "sorter", required = false, defaultValue = "") String sorter,
                           @RequestParam(value = "search", required = false, defaultValue = "") String search,
                           @RequestParam(value = "category", required = false, defaultValue = "") String category) {

        List<BoardResponse> boards;
        if(!search.isEmpty()) {
            boards = boardService.searchBoard(search, category, page, sorter);
            System.out.println(boards.size());
        } else {
            boards = boardService.findBySortRule(sorter, page);
        }
        Long boardSize = (boardService.getBoardsCount(category, search)-1L) / 10L;

        model.addAttribute("search", search);
        model.addAttribute("category", category);
        boardView(member, page, model, sorter, boards, boardSize);
        return "home";
    }
    @GetMapping("/member/boards")
    public String getBoardByMember(
            @AuthenticationPrincipal Member member,
            Model model,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "sorter", required = false, defaultValue = "") String sorter) {

        List<BoardResponse> boards = boardService.findByMemberId(member.getId(), sorter, page);;
        Long boardSize = (boardService.getBoardsCount("myboard", member.getId().toString()) - 1L) / 10L;

        boardView(member, page, model, sorter, boards, boardSize);
        return "memberBoards";
    }

    @GetMapping("/board/post")
    public String boardPostPage(@AuthenticationPrincipal Member member, Model model) throws Exception {
        model.addAttribute("member", member);
        return "post";
    }

    @PostMapping("/board/post")
    public String boardPost(@AuthenticationPrincipal Member member, BoardRequest request, Model model, HttpServletRequest servletRequest) throws Exception {
        model.addAttribute("member", member);
        request.setMemberId(member.getId());
        boardService.saveBoard(request);
        return "redirect:/";
    }

    @GetMapping("/board/{id}")
    public String getBoardById(@AuthenticationPrincipal Member member, @PathVariable(value = "id") Long id, Model model, HttpServletRequest request) {
        BoardResponse board = boardService.findById(id);
        List<FiledataResponse> files = filedataService.findByBoardId(id);
        model.addAttribute("board", board);
        model.addAttribute("member", member);
        model.addAttribute("files", files);
        return "board";
    }

    @GetMapping("/board/{id}/modify")
    public String boardModifyPage(@AuthenticationPrincipal Member member, @PathVariable Long id, Model model) {
        if(!Objects.equals(boardService.findById(id).getMemberId(), member.getId())){
            return "redirect:/";
        }
        model.addAttribute("post_id", id);
        BoardResponse board = boardService.findById(id);
        model.addAttribute("board", board);
        model.addAttribute("member", member);
        return "post";
    }

    @PostMapping("/board/{id}/modify")
    public String boardModify(@AuthenticationPrincipal Member member, @PathVariable(value = "id") Long id, BoardRequest request, Model model) {
        if(!Objects.equals(boardService.findById(id).getMemberId(), member.getId())){
            return "redirect:/";
        }
        boardService.update(request);
        return "redirect:/board/" + id;
    }

    @PostMapping("/board/{id}/delete")
    public String boardRemove(@AuthenticationPrincipal Member member, @PathVariable(value = "id") Long id, HttpServletRequest request) {
        if(!Objects.equals(boardService.findById(id).getMemberId(), member.getId())){
            return "redirect:/";
        }
        boardService.remove(id);
        return "redirect:/";
    }
    @PostMapping("/board/{id}/image/upload")
    public String upload(@AuthenticationPrincipal Member member,
                         @RequestParam MultipartFile[] uploadfile,
                         @PathVariable(value = "id") Long id,
                         Model model) throws IOException, IllegalStateException {
        if(!Objects.equals(boardService.findById(id).getMemberId(), member.getId())){
            return "redirect:/";
        }
        List<FiledataRequest> files = new ArrayList<>();
        for (MultipartFile file : uploadfile) {
            FiledataRequest data = new FiledataRequest(UUID.randomUUID().toString(),
                    file.getOriginalFilename(),
                    file.getContentType(), id, member.getId());
            files.add(data);
            File newFilename = new File(data.getUuid() + "_" + data.getName());
            file.transferTo(newFilename);
        }
        filedataService.save(files);
        model.addAttribute("files", files);
        return "redirect:/board/"+id;
    }

    @GetMapping("/board/{id}/download")
    public ResponseEntity<Resource> download(@ModelAttribute FiledataRequest file) throws IOException {
        Path path = Paths.get(filePath+"/"+file.getUuid()+"_"+file.getName());
        String type = Files.probeContentType(path);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.
                builder("attachment").
                filename(file.getName(), StandardCharsets.UTF_8).build());
        headers.add(HttpHeaders.CONTENT_TYPE, type);

        org.springframework.core.io.Resource resource = new InputStreamResource(Files.newInputStream(path));
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    @PostMapping("/board/{id}/image/delete/{file_id}")
    public String deletdImage(@AuthenticationPrincipal Member member, @ModelAttribute FiledataRequest file,
                              @PathVariable(value = "id") Long id,
                              @PathVariable(value = "file_id") Long fileId) throws IOException {
        if(!Objects.equals(boardService.findById(id).getMemberId(), member.getId())){
            return "redirect:/";
        }
        Path path = Paths.get(filePath+"/"+file.getUuid()+"_"+file.getName());
        filedataService.remove(fileId, String.valueOf(path));
        return "redirect:/board/"+id;
    }


    private void boardView(Member member, int page, Model model, String sorter, List<BoardResponse> boards, Long boardSize) {
        model.addAttribute("member", member);
        boards = simpleDescription(boards);
        model.addAttribute("boards", boards);
        model.addAttribute("board_size", (boardSize));
        model.addAttribute("page", page);
        model.addAttribute("sorter", sorter);
    }

    private List<BoardResponse> simpleDescription(List<BoardResponse> boards) {
        for (BoardResponse board : boards) {
            board.simpleDescription();
        }
        return boards;
    }
}

package SSTT.Backend.controller;

import SSTT.Backend.component.S3Uploader;
import SSTT.Backend.domain.Category;
import SSTT.Backend.domain.Contents;
import SSTT.Backend.domain.Member;
import SSTT.Backend.dto.ContentsDto;
import SSTT.Backend.repository.ContentsRepository;
import SSTT.Backend.service.ContentsService;
import SSTT.Backend.service.MemberService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ContentsController {

    private final S3Uploader uploader;
    private final ContentsRepository contentsRepository;
    private final MemberService memberService;
    private final ContentsService contentsService;

    @ApiImplicitParam(name = "X-AUTH-TOKEN")
    @ApiOperation(value = "게시글 등록", notes = "form-data 형태로 전달해야하며, category의 경우 id(pk)로 전달해야 함")
    @PostMapping("/contents/upload")
    public Long upload(@RequestParam("file") MultipartFile file,
                       @RequestParam("title") String title,
                       @RequestParam("category") Category category,
                       @RequestParam("desc") String desc) throws Exception {
        String path = uploader.upload(file, "static");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        Member member = memberService.findSignId(name);
        return contentsRepository.save(
                Contents.builder()
                        .title(title)
                        .filepath(path)
                        .date(LocalDateTime.now())
                        .category(category)
                        .desc(desc)
                        .member(member)
                        .build()).getId();
    }

    @ApiOperation(value = "게시글 목록 전체 조회", notes = "게시글 목록 전체 전달")
    @GetMapping("/contents")
    public List<Contents> findAll() {
        return contentsService.findAll();
    }
    @ApiOperation(value = "게시글 목록 카테고리별 조회", notes = "카테고리에 따른 게시글 목록 전달")
    @GetMapping("/contents/{categoryId}")
    public List<Contents> findCategory() {
        return contentsService.findCategory();
    }
    /*
    @ApiOperation(value = "카테고리 목록 조회", notes = "카테고리 리스트 전달")
    @GetMapping("/category")
    public List<Category> findAll(Model model) {
        return categoryService.findAll();
    }
     */
    
}

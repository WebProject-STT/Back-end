package SSTT.Backend.controller;

import SSTT.Backend.component.S3Uploader;
import SSTT.Backend.domain.Category;
import SSTT.Backend.domain.Member;
import SSTT.Backend.mapping.ContentsListMapping;
import SSTT.Backend.mapping.ContentsMapping;
import SSTT.Backend.repository.ContentsRepository;
import SSTT.Backend.service.ContentsService;
import SSTT.Backend.service.MemberService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequiredArgsConstructor
public class ContentsController {

    private final S3Uploader uploader;
    private final ContentsRepository contentsRepository;
    private final MemberService memberService;
    private final ContentsService contentsService;

    @ApiImplicitParam(name = "X-AUTH-TOKEN")
    @ApiOperation(value = "게시글 등록", notes = "form-data 형태로 전달해야하며, category의 경우 id(pk)로 전달해야 함")
    @PostMapping("/contents")
    public Long uploadTest(@RequestParam("file") MultipartFile file,
                       @RequestParam("title") String title,
                       @RequestParam("category") Category category,
                       @RequestParam("desc") String desc,
                           @RequestParam("subject_nums") Integer subjectNum) throws Exception {

        // member 조회
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        Member member = memberService.findSignId(name);

        return contentsService.createContents(member, file, title, category, desc, subjectNum);
    }

    @ApiImplicitParam(name = "X-AUTH-TOKEN")
    @ApiOperation(value = "게시글 목록 전체 조회", notes = "게시글 목록 전체 전달")
    @GetMapping("/contents")
    public List<ContentsListMapping> findAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        Member member = memberService.findSignId(name);
        return contentsService.findAll(member);
    }

    @ApiImplicitParam(name = "X-AUTH-TOKEN")
    @ApiOperation(value = "게시글 목록 카테고리별 조회", notes = "카테고리에 따른 게시글 목록 전달")
    @GetMapping("/contents/list/{categoryId}")
    public List<ContentsListMapping> findCategory(@PathVariable("categoryId") Long categoryId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        Member member = memberService.findSignId(name);
        return contentsService.findCategory(member, categoryId);
    }

    @ApiImplicitParam(name = "X-AUTH-TOKEN")
    @ApiOperation(value = "게시글 조회", notes = "특정 게시글 내용 반환")
    @GetMapping("/contents/{contentsId}")
    public List<ContentsMapping> findContents(@PathVariable("contentsId") Long contentsId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        Member member = memberService.findSignId(name);
        return contentsService.findContents(member, contentsId);
    }

    @ApiImplicitParam(name = "X-AUTH-TOKEN")
    @ApiOperation(value = "게시글 삭제", notes = "특정 게시글 삭제")
    @DeleteMapping("/contents/{contentsId}")
    public ResponseEntity<?> delete(@PathVariable("contentsId") Long contentsId) {
        contentsService.delete(contentsId);
        return new ResponseEntity<>("contents delete", HttpStatus.OK);
    }

    @ApiImplicitParam(name = "X-AUTH-TOKEN")
    @ApiOperation(value = "여러 게시글 삭제", notes = "선택한 여러 게시글 삭제")
    @DeleteMapping("/contents")
    public ResponseEntity<?> deleteContents(@RequestParam("deleteList") List<Long> ids) {
        contentsService.deleteContents(ids);
        return new ResponseEntity<>("contents list delete", HttpStatus.OK);
    }

    /*
    @ApiOperation(value = "카테고리 삭제", notes = "카테고리 삭제 성공 시 true 반환")
    @DeleteMapping("/category/{categoryId}")
    public Boolean delete(@PathVariable("categoryId") Long id) {
        return categoryService.delete(id);
    }
     */
    
}

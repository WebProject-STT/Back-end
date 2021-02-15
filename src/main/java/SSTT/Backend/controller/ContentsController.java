package SSTT.Backend.controller;

import SSTT.Backend.component.S3Uploader;
import SSTT.Backend.domain.Category;
import SSTT.Backend.domain.Contents;
import SSTT.Backend.domain.Member;
import SSTT.Backend.mapping.ContentsListMapping;
import SSTT.Backend.mapping.ContentsMapping;
import SSTT.Backend.repository.ContentsRepository;
import SSTT.Backend.service.ContentsService;
import SSTT.Backend.service.MemberService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @PostMapping("/contents")
    public Long upload(@RequestParam("file") MultipartFile file,
                       @RequestParam("title") String title,
                       @RequestParam("category") Category category,
                       @RequestParam("desc") String desc) throws Exception {
        String path = uploader.upload(file, "static");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        System.out.println("===========name==============");
        System.out.println(name);
        System.out.println("===============================");
        Member member = memberService.findSignId(name);
        System.out.println("============member=============");
        System.out.println(member);
        System.out.println(member.getId());
        System.out.println("===============================");
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

    @ApiImplicitParam(name = "X-AUTH-TOKEN")
    @ApiOperation(value = "게시글 목록 전체 조회", notes = "게시글 목록 전체 전달")
    @GetMapping("/contents")
    public List<ContentsListMapping> findAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        System.out.println("===========find-name==============");
        System.out.println(name);
        System.out.println("===============================");
        Member member = memberService.findSignId(name);
        System.out.println("============find-member=============");
        System.out.println(member.getId());
        System.out.println("===============================");
        return contentsService.findAll(member);
    }

    @ApiImplicitParam(name = "X-AUTH-TOKEN")
    @ApiOperation(value = "게시글 목록 카테고리별 조회", notes = "카테고리에 따른 게시글 목록 전달")
    @GetMapping("/contents/list/{categoryId}")
    public List<ContentsListMapping> findCategory(@PathVariable("categoryId") Long categoryId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        System.out.println("===========find-name(category)==============");
        System.out.println(name);
        System.out.println("===============================");
        Member member = memberService.findSignId(name);
        System.out.println("============find-member(category)=============");
        System.out.println(member.getId());
        System.out.println("===============================");
        return contentsService.findCategory(member, categoryId);
    }

    @ApiImplicitParam(name = "X-AUTH-TOKEN")
    @ApiOperation(value = "게시글 조회", notes = "특정 게시글 내용 반환")
    @GetMapping("/contents/{contentsId}")
    public List<ContentsMapping> findContents(@PathVariable("contentsId") Long contentsId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        System.out.println("===========find-name(contents)==============");
        System.out.println(name);
        System.out.println("===============================");
        Member member = memberService.findSignId(name);
        System.out.println("============find-member(contents)=============");
        System.out.println(member.getId());
        System.out.println("===============================");
        return contentsService.findContents(member, contentsId);
    }


    
}

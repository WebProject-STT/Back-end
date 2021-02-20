package SSTT.Backend.controller;

import SSTT.Backend.domain.Category;
import SSTT.Backend.domain.Member;
import SSTT.Backend.dto.ContentsUpdateDto;
import SSTT.Backend.service.ContentsService;
import SSTT.Backend.service.MemberService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequiredArgsConstructor
public class ContentsController {

    private final MemberService memberService;
    private final ContentsService contentsService;

//    @ApiImplicitParam(name = "X-AUTH-TOKEN")
    @ApiImplicitParam(name = "memberId")
    @ApiOperation(value = "게시글 등록", notes = "form-data 형태로 전달해야하며, category의 경우 id(pk)로 전달해야 함")
    @PostMapping("/contents")
    public Object uploadTest(@RequestParam("file") MultipartFile file,
                             @RequestParam("title") String title,
                             @RequestParam("category") Category category,
                             @RequestParam("desc") String desc, @RequestParam("subject_nums") Integer subjectNum,
                             @RequestHeader Long memberId) throws Exception {

        // member 조회
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String name = authentication.getName();
//        Member member = memberService.findSignId(name);
        try {
            Member member = memberService.findById(memberId);
            return contentsService.createContents(member, file, title, category, desc, subjectNum);
        } catch (IllegalArgumentException e) {
            return "false";
        }

    }

//    @ApiImplicitParam(name = "X-AUTH-TOKEN")
    @ApiImplicitParam(name = "memberId")
    @ApiOperation(value = "게시글 목록 전체 조회", notes = "게시글 목록 전체 전달")
    @GetMapping("/contents")
    public Object findAll(@RequestHeader Long memberId) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String name = authentication.getName();
//        Member member = memberService.findSignId(name);
        try {
            Member member = memberService.findById(memberId);
            return contentsService.findAll(member);
        } catch (IllegalArgumentException e) {
            return "false";
        }
    }

//    @ApiImplicitParam(name = "X-AUTH-TOKEN")
    @ApiImplicitParam(name = "memberId")
    @ApiOperation(value = "게시글 목록 카테고리별 조회", notes = "카테고리에 따른 게시글 목록 전달")
    @GetMapping("/contents/list/{categoryId}")
    public Object findCategory(@PathVariable("categoryId") Long categoryId, @RequestHeader Long memberId) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String name = authentication.getName();
//        Member member = memberService.findSignId(name);

        try {
            Member member = memberService.findById(memberId);
            return contentsService.findCategory(member, categoryId);
        } catch (IllegalArgumentException e) {
            return "false";
        }

    }

//    @ApiImplicitParam(name = "X-AUTH-TOKEN")
    @ApiImplicitParam(name = "memberId")
    @ApiOperation(value = "게시글 조회", notes = "특정 게시글 내용 반환")
    @GetMapping("/contents/{contentsId}")
    public Object findContents(@PathVariable("contentsId") Long contentsId, @RequestHeader Long memberId) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String name = authentication.getName();
//        Member member = memberService.findSignId("test");

        try {
            Member member = memberService.findById(memberId);
            return contentsService.findContents(member, contentsId);
        } catch (IllegalArgumentException e) {
            return "false";
        }
    }

//    @ApiImplicitParam(name = "X-AUTH-TOKEN")
    @ApiImplicitParam(name = "memberId")
    @ApiOperation(value = "게시글 삭제", notes = "특정 게시글 삭제")
    @DeleteMapping("/contents/{contentsId}")
    public Object delete(@PathVariable("contentsId") Long contentsId, @RequestHeader Long memberId) {
        try {
            Member member = memberService.findById(memberId);
            contentsService.delete(contentsId);
            return new ResponseEntity<>("contents delete", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return "false";
        }
    }

//    @ApiImplicitParam(name = "X-AUTH-TOKEN")
    @ApiImplicitParam(name = "memberId")
    @ApiOperation(value = "여러 게시글 삭제", notes = "선택한 여러 게시글 삭제")
    @DeleteMapping("/contents")
    public Object deleteContents(@RequestParam("deleteList") List<Long> ids, @RequestHeader Long memberId) {

        try {
            Member member = memberService.findById(memberId);
            contentsService.deleteContents(ids);
            return new ResponseEntity<>("contents list delete", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return "false";
        }
    }

//    @ApiImplicitParam(name = "X-AUTH-TOKEN")
    @ApiImplicitParam(name = "memberId")
    @ApiOperation(value = "게시글 내용 수정", notes = "특정 게시글 내용 수정")
    @PutMapping("/contents/{contentsId}")
    public Object update(@PathVariable("contentsId") Long contentsId, @RequestBody @Valid ContentsUpdateDto contentsUpdateDto, @RequestHeader Long memberId) {

        // member 조회
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String name = authentication.getName();
//        Member member = memberService.findSignId(name);

        try {
            Member member = memberService.findById(memberId);
            return contentsService.updateContents(contentsId, member, contentsUpdateDto);
        } catch (IllegalArgumentException e) {
            return "false";
        }

    }

//    @ApiImplicitParam(name = "X-AUTH-TOKEN")
    @ApiImplicitParam(name = "memberId")
    @ApiOperation(value = "게시글 파일 수정", notes = "특정 게시글 파일 수정")
    @PostMapping("/contents/{contentsId}")
    public Object updateFile(@PathVariable("contentsId") Long contentsId,
                           @RequestParam("file") MultipartFile file,
                           @RequestParam("subject_nums") Integer subjectNum,
                           @RequestHeader Long memberId){
        // member 조회
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String name = authentication.getName();
//        Member member = memberService.findSignId(name);

        try {
            Member member = memberService.findById(memberId);
            return contentsService.updateContentsFile(contentsId, member, file, subjectNum);
        } catch (IllegalArgumentException e) {
            return "false";
        }

    }
    
}

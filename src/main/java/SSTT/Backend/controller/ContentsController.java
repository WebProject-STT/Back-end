package SSTT.Backend.controller;

import SSTT.Backend.component.S3Uploader;
import SSTT.Backend.domain.Category;
import SSTT.Backend.domain.Contents;
import SSTT.Backend.domain.Member;
import SSTT.Backend.dto.ContentsDto;
import SSTT.Backend.repository.ContentsRepository;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class ContentsController {

    private final S3Uploader uploader;
    private final ContentsRepository contentsRepository;

    @ApiImplicitParam(name = "X-AUTH-TOKEN")
    @ApiOperation(value = "게시글 등록", notes = "form-data 형태로 전달해야하며, category 및 member의 경우 id(pk)로 전달해야 함")
    @PostMapping("/contents")
    public Long upload(@RequestParam("file") MultipartFile file,
                       @RequestParam("title") String title,
                       @RequestParam("category") Category category,
                       @RequestParam("desc") String desc,
                       @RequestParam("member") Member member) throws Exception {
        String path = uploader.upload(file, "static");
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
    
}

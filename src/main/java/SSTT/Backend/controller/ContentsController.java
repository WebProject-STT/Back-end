package SSTT.Backend.controller;

import SSTT.Backend.dto.ContentsDto;
import SSTT.Backend.service.ContentsService;
import SSTT.Backend.service.S3Service;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@AllArgsConstructor
public class ContentsController {
    private S3Service s3Service;
    private ContentsService contentsService;

    @ApiOperation(value = "게시글 등록")
    @PostMapping("/contents")
    public String upload(ContentsDto contentsDto, MultipartFile file) throws IOException {
        String path = s3Service.upload(file);
        contentsDto.setFilepath(path);
        contentsService.savePost(contentsDto);

        return "home";
    }

//    @GetMapping("/contents")
//    public String upload(Model model) {
//        model.addAttribute("contents", new ContentsDto());
//        return "upload";
//    }
//
//    @ApiOperation(value = "게시글 등록")
//    @PostMapping("/contents")
//    public String upload(ContentsDto contentsDto, MultipartFile file) throws IOException {
//        String path = s3Service.upload(file);
//        contentsDto.setFilepath(path);
//        contentsService.savePost(contentsDto);
//
//        return "home";
//    }
}

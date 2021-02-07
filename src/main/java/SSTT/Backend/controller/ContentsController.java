package SSTT.Backend.controller;

import SSTT.Backend.dto.ContentsDto;
import SSTT.Backend.dto.MemberDto;
import SSTT.Backend.service.ContentsService;
import SSTT.Backend.service.S3Service;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@AllArgsConstructor
public class ContentsController {
    private S3Service s3Service;
    private ContentsService contentsService;

    @GetMapping("/upload")
    public String upload(Model model) {
        model.addAttribute("contents", new ContentsDto());
        return "upload";
    }

    @PostMapping("/upload")
    public String upload(ContentsDto contentsDto, MultipartFile file) throws IOException {
        String path = s3Service.upload(file);
        contentsDto.setFilepath(path);
        contentsService.savePost(contentsDto);

        return "home";
    }
}

package SSTT.Backend.service;

import SSTT.Backend.component.ApiClient;
import SSTT.Backend.component.S3Uploader;
import SSTT.Backend.domain.*;
import SSTT.Backend.mapping.ContentsListMapping;
import SSTT.Backend.mapping.ContentsMapping;
import SSTT.Backend.repository.ContentsRepository;
import SSTT.Backend.repository.SummaryRepository;
import SSTT.Backend.repository.TagRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ContentsService {

    private final S3Uploader uploader;
    private final ApiClient apiClient;
    private final TagRepository tagRepository;
    private final SummaryRepository summaryRepository;
    private final ContentsRepository contentsRepository;

    @SneakyThrows
    @Transactional
    public Long createContents(Member member, MultipartFile file, String title, Category category, String desc, Integer subjectNum) {

        // DB에 저장할 url
        String path = uploader.upload(file, "static");

        // 전송할 데이터 생성
        String extension = file.getOriginalFilename().split("\\.")[1];
        String[] pathList = path.split("/");
        String s3ulr = "s3://sstt/static/" + pathList[4];
        String filename = pathList[4];

        // 외부 API 통신을 통해 JSON 파일 받아와야 함 (STT 처리된 파일)
        Map<String, Object> params = apiClient.getApi(filename, extension, s3ulr, subjectNum);

        System.out.println("==========================");
        System.out.println(params);
        System.out.println("==========================");

        // origin
        String origin = (String) params.get("origin");



        // tag
        List<String> tags = (List<String>) params.get("tagList");

        List<Tag> tagList = new ArrayList<>();
        for (String tag : tags){
            System.out.println(tag);
            Tag addTag = tagRepository.save(Tag.builder().name(tag).build());
            tagList.add(addTag);
        }

        // summary
        List<String> summaryTitle = (List<String>) params.get("title");
        List<String> summaryDesc = (List<String>) params.get("desc");

        List<Summary> summaryList = new ArrayList<>();
        System.out.println("==========================");
        for (int i = 0; i < summaryTitle.size(); i++) {
            System.out.println(summaryTitle.get(i) + summaryDesc.get(i));
            Summary summary = summaryRepository.save(Summary.builder().title(summaryTitle.get(i)).desc(summaryDesc.get(i)).build());
            summaryList.add(summary);
        }
        System.out.println("==========================");

        System.out.println("====contents 생성====");
        Contents contents = Contents.createContents(member, category, title, desc, path, origin, tagList, summaryList);

        contentsRepository.save(contents);

        return contents.getId();
    }

    @Transactional
    public List<ContentsListMapping> findAll(Member member) {
        List<ContentsListMapping> findContents = contentsRepository.findAllByMemberId(member.getId());
        return findContents;
    }

    @Transactional
    public List<ContentsListMapping> findCategory(Member member, Long categoryId) {
        return contentsRepository.findAllByMemberIdAndCategoryId(member.getId(), categoryId);
    }

    @Transactional
    public List<ContentsMapping> findContents(Member member, Long contentsId) {
        return contentsRepository.findByIdAndAndMemberId(contentsId, member.getId());
    }

    @Transactional
    public void delete(Long contentsId) {
        contentsRepository.deleteById(contentsId);
    }

    public void deleteContents(List<Long> ids) {
        for (Long id : ids) contentsRepository.deleteById(id);
    }
}

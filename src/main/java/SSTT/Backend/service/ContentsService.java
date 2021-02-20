package SSTT.Backend.service;

import SSTT.Backend.component.ApiClient;
import SSTT.Backend.component.S3Uploader;
import SSTT.Backend.domain.*;
import SSTT.Backend.dto.ContentsUpdateDto;
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
import java.util.Optional;

@Service
@AllArgsConstructor
public class ContentsService {

    private final S3Uploader uploader;
    private final ApiClient apiClient;
    private final TagRepository tagRepository;
    private final SummaryRepository summaryRepository;
    private final ContentsRepository contentsRepository;

    @Transactional
    public Long createContents(Member member, MultipartFile file, String title, Category category, String desc, Integer subjectNum) {

        // sstt 처리
        List ssttResult = sstt(file, subjectNum);

        String path = (String) ssttResult.get(0);
        String origin = (String) ssttResult.get(1);
        List<Tag> tagList = (List<Tag>) ssttResult.get(2);
        List<Summary> summaryList = (List<Summary>) ssttResult.get(3);

        // contents 생성
        Contents contents = Contents.createContents(member, category, title, desc, path, origin, tagList, summaryList);

        contentsRepository.save(contents);

        return contents.getId();
    }

    // 파일 업데이트
    @Transactional
    public Long updateContentsFile(Long id, Member member, MultipartFile file, Integer subjectNum) {
        //String title, Category category, String desc

        // 기존 정보
        Optional<Contents> oddContents = contentsRepository.findById(id);

        // 기존 tag 및 summary 제거 필요
        List<Tag> oddTagList = oddContents.get().getTagList();
        for (Tag tag : oddTagList) {
            tagRepository.delete(tag);
        }
        List<Summary> oddSummaryList = oddContents.get().getSummaryList();
        for (Summary summary : oddSummaryList) {
            summaryRepository.delete(summary);
        }
        oddContents.get().setTagList(null);
        oddContents.get().setSummaryList(null);

        // sstt 처리
        List ssttResult = sstt(file, subjectNum);

        String path = (String) ssttResult.get(0);
        String origin = (String) ssttResult.get(1);
        List<Tag> tagList = (List<Tag>) ssttResult.get(2);
        List<Summary> summaryList = (List<Summary>) ssttResult.get(3);

        // contents 재구성
        Contents contents = Contents.updateContents(id, member, oddContents.get().getCategory(), oddContents.get().getTitle(), oddContents.get().getDesc(), path, origin, tagList, summaryList);

        contentsRepository.save(contents);

        return contents.getId();
    }

    // 내용 업데이트
    @Transactional
    public Long updateContents(Long id,Member member, ContentsUpdateDto contentsUpdateDto){

        Optional<Contents> oddContents = contentsRepository.findById(id);

        // tag는 새로 생성해야하므로 제거
        List<Tag> oddTagList = oddContents.get().getTagList();
        for (Tag tag : oddTagList) {
            tagRepository.delete(tag);
        }
        oddContents.get().setTagList(null);
        oddContents.get().setSummaryList(null);

        // tag 및 summary 변경
        List<Tag> tagList = new ArrayList<>();
        List<Tag> newTagList = contentsUpdateDto.getTagList();
        for (Tag tag : newTagList) {
            Tag addTag = tagRepository.save(Tag.builder().name(tag.getName()).build());
            tagList.add(addTag);
        }
        List<Summary> summaryList = new ArrayList<>();
        List<Summary> newSummaryList = contentsUpdateDto.getSummaryList();
        for (Summary summary : newSummaryList) {
            Summary addSummary = summaryRepository.save(summary);
            summaryList.add(addSummary);
        }

        // contents 재구성
        Contents contents = Contents.updateContents(id, member, contentsUpdateDto.getCategory(), contentsUpdateDto.getTitle(), contentsUpdateDto.getDesc(), oddContents.get().getFilepath(), contentsUpdateDto.getOrigin(), tagList, summaryList);

        contentsRepository.save(contents);

        return contents.getId();
    }

    // sstt 처리하는 부분
    @SneakyThrows
    public List sstt(MultipartFile file, Integer subjectNum){
        // DB에 저장할 url
        String path = uploader.upload(file, "static");

        // 전송할 데이터 생성
        String extension = file.getOriginalFilename().split("\\.")[1];
        String[] pathList = path.split("/");
        String s3ulr = "s3://sstt/static/" + pathList[4];
        String filename = pathList[4];

        // 외부 API 통신을 통해 JSON 파일 받아와야 함 (STT 처리된 파일)
        Map<String, Object> params = apiClient.getApi(filename, extension, s3ulr, subjectNum);

        // origin
        String origin = (String) params.get("origin");

        // tag
        List<String> tags = (List<String>) params.get("tagList");

        List<Tag> tagList = new ArrayList<>();
        for (String tag : tags){
            Tag addTag = tagRepository.save(Tag.builder().name(tag).build());
            tagList.add(addTag);
        }

        // summary
        List<String> summaryTitle = (List<String>) params.get("title");
        List<String> summaryDesc = (List<String>) params.get("desc");

        List<Summary> summaryList = new ArrayList<>();

        for (int i = 0; i < summaryTitle.size(); i++) {
            Summary summary = summaryRepository.save(Summary.builder().title(summaryTitle.get(i)).desc(summaryDesc.get(i)).build());
            summaryList.add(summary);
        }

        List result = new ArrayList();
        result.add(path);
        result.add(origin);
        result.add(tagList);
        result.add(summaryList);

        return result;
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
    public void delete(Long id) {

        Optional<Contents> oddContents = contentsRepository.findById(id);

        // tag 및 summary를 먼저 제거해야 함
        List<Tag> oddTagList = oddContents.get().getTagList();
        for (Tag tag : oddTagList) {
            tagRepository.delete(tag);
        }
        List<Summary> oddSummaryList = oddContents.get().getSummaryList();
        for (Summary summary : oddSummaryList) {
            summaryRepository.delete(summary);
        }
        oddContents.get().setTagList(null);
        oddContents.get().setSummaryList(null);

        contentsRepository.deleteById(id);
    }

    @Transactional
    public void deleteContents(List<Long> ids) {
        for (Long id : ids) {
            delete(id);
        };
    }

}

package SSTT.Backend.dto;

import SSTT.Backend.domain.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContentsDto {

    private Long id; // 게시글 번호
    private Member member; // 회원 번호
    private Category category; // 카테고리 번호
    private String title; // 게시글 제목
    private String desc; // 게시글 설명
    private LocalDateTime date; // 게시글 생성일
    private String origin; // 게시글 원본
    private String filepath; // 게시글 파일 url
    private List<Tag> tagList = new ArrayList<>(); // 태그 리스트
    private List<Summary> summaryList = new ArrayList<>(); // 요약 리스트

    // Contents 객체로 변환
    public Contents toEntity() {
        return Contents.builder()
                .id(id)
                .member(member)
                .category(category)
                .title(title)
                .desc(desc)
                .date(date)
                .origin(origin)
                .filepath(filepath)
                .tagList(tagList)
                .summaryList(summaryList)
                .build();
    }
}

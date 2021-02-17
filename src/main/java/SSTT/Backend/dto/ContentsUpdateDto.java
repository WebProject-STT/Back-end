package SSTT.Backend.dto;

import SSTT.Backend.domain.Category;
import SSTT.Backend.domain.Member;
import SSTT.Backend.domain.Summary;
import SSTT.Backend.domain.Tag;
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
public class ContentsUpdateDto {

    private Category category; // 카테고리 번호
    private String title; // 게시글 제목
    private String desc; // 게시글 설명
    private String origin; // 게시글 원본
    private List<Tag> tagList = new ArrayList<>(); // 태그 리스트
    private List<Summary> summaryList = new ArrayList<>(); // 요약 리스트

}

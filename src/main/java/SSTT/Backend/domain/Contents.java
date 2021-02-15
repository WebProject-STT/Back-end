package SSTT.Backend.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Contents {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ct_id")
    private long id; // 게시글 번호

    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // 회원 번호

    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name = "cg_id")
    private Category category; // 카테고리명

    @Column(name = "ct_title")
    private String title; // 게시글 제목

    @Column(name = "ct_desc")
    private String desc; // 게시글 설명

    @Column(name = "ct_date")
    private LocalDateTime date; // 게시글 생성일

    @Column(name = "ct_origin", columnDefinition = "TEXT")
    private String origin; // 게시글 원본

    @Column(name = "ct_file", columnDefinition = "TEXT")
    private String filepath; // 게시글 파일 url

    @OneToMany(mappedBy = "contents")
    private List<Tag> tagList = new ArrayList<>(); // 태그 리스트

    @OneToMany(mappedBy = "contents")
    private List<Summary> summaryList = new ArrayList<>(); // 요약 리스트

    // 연관관계 편의 메소드
//    public void setMember(Member member){
//        this.member = member;
//        member.getContentsList().add(this);
//    }

//    public void setCategory(Category category){
//        this.category = category;
//        member.getContentsList().add(this);
//    }

    public void addTag(Tag tag){
        tagList.add(tag);
        tag.setContents(this);
    }

    public void addSummary(Summary summary){
        summaryList.add(summary);
        summary.setContents(this);
    }
}

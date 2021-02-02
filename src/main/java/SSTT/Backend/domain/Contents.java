package SSTT.Backend.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Contents {

    @Id @GeneratedValue
    private long ct_idx; // 게시글 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    private User user; // 회원 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cg_idx")
    private Category category; // 카테고리 번호

    private String ct_title; // 게시글 제목
    private String ct_desc; // 게시글 설명
    private LocalDateTime ct_date; // 게시글 생성일
    private String ct_origin; // 게시글 원본

    @Embedded
    private File ct_file; // 게시글 파일 (수정 필요)

    @OneToMany(mappedBy = "contents")
    private List<Tag> tagList = new ArrayList<>(); // 태그 리스트

    @OneToMany(mappedBy = "contents")
    private List<Summary> summaryList = new ArrayList<>(); // 요약 리스트

    // 연관관계 편의 메소드
    public void setUser(User user){
        this.user = user;
        user.getContentsList().add(this);
    }

    public void setCategory(Category category){
        this.category = category;
        user.getContentsList().add(this);
    }

    public void addTag(Tag tag){
        tagList.add(tag);
        tag.setContents(this);
    }

    public void addSummary(Summary summary){
        summaryList.add(summary);
        summary.setContents(this);
    }
}

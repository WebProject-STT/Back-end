package SSTT.Backend.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Summary {

    @Id @GeneratedValue
    private Long sum_idx; // 요약글 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ct_idx")
    private Contents contents; // 게시글 번호

    private String sum_title; // 요약글 제목
    private String sum_desc; // 요약글 설명
}

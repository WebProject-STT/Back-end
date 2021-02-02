package SSTT.Backend.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Summary {

    @Id @GeneratedValue
    @Column(name = "sum_id")
    private Long id; // 요약글 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ct_id")
    private Contents contents; // 게시글 번호

    @Column(name = "sum_title")
    private String title; // 요약글 제목

    @Column(name = "sum_desc")
    private String desc; // 요약글 설명
}

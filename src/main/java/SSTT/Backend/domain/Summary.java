package SSTT.Backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Summary {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sum_id")
    private Long id; // 요약글 번호

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ct_id")
    private Contents contents; // 게시글 번호

    @Column(name = "sum_title")
    private String title; // 요약글 제목

    @Column(name = "sum_desc", columnDefinition = "TEXT")
    private String desc; // 요약글 설명
}

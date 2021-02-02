package SSTT.Backend.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Tag {

    @Id @GeneratedValue
    private Long tag_idx; // 태그 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ct_idx")
    private Contents contents; // 게시글 번호

    private String tag_name; // 태그명
}

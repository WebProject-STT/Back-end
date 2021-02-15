package SSTT.Backend.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tag {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id; // 태그 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ct_id")
    private Contents contents; // 게시글 번호

    @Column(name = "tag_name")
    private String name; // 태그명
}

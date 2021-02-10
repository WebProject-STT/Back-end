package SSTT.Backend.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cg_id")
    private Long id; // 카테고리 번호

    // 회원에 관계 없이 모든 카테고리는 동일하게 생성됨
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id")
//    private Member member; // 회원 번호

    @Column(name = "cg_title")
    private String title; // 카테고리명

//    @OneToMany(mappedBy = "category")
//    private List<Contents> contentsList = new ArrayList<>(); // 게시글 리스트

    // 연관관계 편의 메소드
//    public void setMember(Member member) {
//        this.member = member;
//        member.getCategoryList().add(this);
//    }

    @Builder
    public Category(Long id, String title) {
        this.id = id;
        this.title = title;
        //this.contentsList = contentsList;
    }
}

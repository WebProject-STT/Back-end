package SSTT.Backend.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {

    @Id @GeneratedValue
    @Column(name = "cg_id")
    private Long cg_id; // 카테고리 번호

    @Column(name = "cg_title")
    private String title; // 카테고리명

    @OneToMany(mappedBy = "category")
    private List<Contents> contentsList = new ArrayList<>(); // 게시글 리스트
}

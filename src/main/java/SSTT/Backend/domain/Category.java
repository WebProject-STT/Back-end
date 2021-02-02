package SSTT.Backend.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {

    @Id @GeneratedValue
    private Long cg_idx; // 카테고리 번호

    private String cg_title; // 카테고리명

    @OneToMany(mappedBy = "category")
    private List<Contents> contentsList = new ArrayList<>(); // 게시글 리스트
}

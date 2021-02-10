package SSTT.Backend.dto;

import SSTT.Backend.domain.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {

    private Long id; // 카테고리 번호
//    private Member member; // 회원 번호
    private String title; // 카테고리명
//    private List<Contents> contentsList = new ArrayList<>(); // 게시글 리스트

    public Category toEntity() {
        return Category.builder()
                .id(id)
                .title(title)
                .build();
    }

    @Builder
    public CategoryDto(Long id, String title) {
        this.id = id;
        this.title = title;
    }
}

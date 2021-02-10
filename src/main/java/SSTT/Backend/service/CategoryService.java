package SSTT.Backend.service;

import SSTT.Backend.domain.Category;
import SSTT.Backend.dto.CategoryDto;
import SSTT.Backend.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor // final 선언과 관련된 lombok 기능
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public Long create(CategoryDto categoryDto) {
        return categoryRepository.save(categoryDto.toEntity()).getId();
    }

    @Transactional
    public boolean delete(Long id) {
        categoryRepository.deleteById(id);
        return true;
    }

    @Transactional
    public List<CategoryDto> findAll() {
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryDto> categoryDtoList = new ArrayList<>();

        for (Category category : categoryList) {
            CategoryDto categoryDto = CategoryDto.builder()
                    .id(category.getId())
                    .title(category.getTitle())
                    .build();
            categoryDtoList.add(categoryDto);
        }

        return categoryDtoList;

    }


    /*
    * return memberRepository.save(
                Member.builder()
                        .signId(member.getSignId())
                        .name(member.getName())
                        .pwd(passwordEncoder.encode(member.getPwd()))
                        .email(member.getEmail())
                        .roles(Collections.singletonList("USER")) // 최초 가입 시 USER로 설정
                        .signupDt(LocalDateTime.now())
                        .build()).getId();
                        *
       public Member findSignId(String signId){
        return memberRepository.findBySignId(signId)
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 ID 입니다."));
    }
    * */
}

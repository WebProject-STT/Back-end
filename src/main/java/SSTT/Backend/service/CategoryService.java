package SSTT.Backend.service;

import SSTT.Backend.domain.Category;
import SSTT.Backend.dto.CategoryDto;
import SSTT.Backend.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    @Transactional
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

}

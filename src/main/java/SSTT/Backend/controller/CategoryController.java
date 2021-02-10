package SSTT.Backend.controller;

import SSTT.Backend.domain.Category;
import SSTT.Backend.dto.CategoryDto;
import SSTT.Backend.repository.CategoryRepository;
import SSTT.Backend.service.CategoryService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @ApiOperation(value = "카테고리 생성", notes = "카테고리 번호 반환")
    @PostMapping("/category/create")
    public Long create(@RequestBody @Valid CategoryDto categoryDto) {
        return categoryService.create(categoryDto);
    }

    @ApiOperation(value = "카테고리 삭제", notes = "카테고리 삭제 성공 시 true 반환")
    @PostMapping("/category/delete")
    public Boolean delete(@RequestBody @Valid CategoryDto categoryDto) {
        return categoryService.delete(categoryDto.getId());
    }

    @ApiOperation(value = "카테고리 목록 조회", notes = "카테고리 리스트 전달")
    @GetMapping("/category")
    public List<CategoryDto> findAll(Model model) {
        return categoryService.findAll();
    }

}

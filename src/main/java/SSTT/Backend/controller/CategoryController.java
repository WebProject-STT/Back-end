package SSTT.Backend.controller;

import SSTT.Backend.domain.Category;
import SSTT.Backend.dto.CategoryDto;
import SSTT.Backend.service.CategoryService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @ApiOperation(value = "카테고리 생성", notes = "카테고리 번호 반환")
    @PostMapping("/category")
    public Long create(@RequestBody @Valid CategoryDto categoryDto) {
        return categoryService.create(categoryDto);
    }

    @ApiOperation(value = "카테고리 삭제", notes = "카테고리 삭제 성공 시 true 반환")
    @DeleteMapping("/category/{categoryId}")
    public ResponseEntity<?> delete(@PathVariable("categoryId") Long id) {
        categoryService.delete(id);
        return new ResponseEntity<>("category delete", HttpStatus.OK);
    }

    @ApiOperation(value = "카테고리 목록 조회", notes = "카테고리 리스트 전달")
    @GetMapping("/category")
    public List<Category> findAll() {
        return categoryService.findAll();
    }

}

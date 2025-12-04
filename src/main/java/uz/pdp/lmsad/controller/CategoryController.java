package uz.pdp.lmsad.controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lmsad.dto.category.CategoryDto;
import uz.pdp.lmsad.dto.category.CreateCategoryDto;
import uz.pdp.lmsad.dto.category.UpdateCategoryDto;
import uz.pdp.lmsad.dto.course.CourseDto;
import uz.pdp.lmsad.dto.course.CreateCourseDto;
import uz.pdp.lmsad.service.CategoryService;
import uz.pdp.lmsad.service.CourseService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
@PreAuthorize("hasRole('ADMIN')")
@SecurityRequirement(name = "BearerAuth")
public class CategoryController {


    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDto>  createCategory(@RequestBody CreateCategoryDto dto) {
        return ResponseEntity.status(201).body(categoryService.create(dto));
    }

    @GetMapping("/{id}")
    public CategoryDto  getCategory(@PathVariable String id) {
        return categoryService.get(id);
    }

    @GetMapping
    public List<CategoryDto> getAllCategory() {
        return categoryService.getAll();
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> deleteCategory(@PathVariable String id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/{id}")
    public  CategoryDto update(@PathVariable String id, @RequestBody UpdateCategoryDto dto) {
        return categoryService.update(id, dto);
    }
}

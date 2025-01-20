package com.cleytonongaratto.desafioanotaai.controllers;

import com.cleytonongaratto.desafioanotaai.domain.category.Category;
import com.cleytonongaratto.desafioanotaai.domain.category.CategoryDTO;
import com.cleytonongaratto.desafioanotaai.repositories.CategoryRepository;
import com.cleytonongaratto.desafioanotaai.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private CategoryService service;

    public CategoryController(CategoryService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Category> insert(@RequestBody CategoryDTO categoryData){
    Category newCategory = this.service.insert(categoryData);
    return ResponseEntity.ok().body(newCategory);
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAll(){
        List<Category> categories = this.service.getAll();
        return ResponseEntity.ok().body(categories);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<Category> update(@RequestParam("categoryId") String categoryId, @RequestBody CategoryDTO categoryData){
        Category updatedCategory = this.service.update(categoryId, categoryData);
        return ResponseEntity.ok().body(updatedCategory);

    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Category> delete(@RequestParam("categoryId") String categoryId){
        this.service.delete(categoryId);
        return ResponseEntity.noContent().build();
    }
}

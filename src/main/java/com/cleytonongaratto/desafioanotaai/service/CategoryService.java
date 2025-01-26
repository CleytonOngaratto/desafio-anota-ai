package com.cleytonongaratto.desafioanotaai.service;

import com.cleytonongaratto.desafioanotaai.domain.category.Category;
import com.cleytonongaratto.desafioanotaai.domain.category.CategoryDTO;
import com.cleytonongaratto.desafioanotaai.domain.category.exceptions.CategoryNotFoundException;
import com.cleytonongaratto.desafioanotaai.repositories.CategoryRepository;
import com.cleytonongaratto.desafioanotaai.service.aws.AwsSnsService;
import com.cleytonongaratto.desafioanotaai.service.aws.MessageDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository repository;
    private final AwsSnsService snsService;

    public CategoryService(CategoryRepository repository, AwsSnsService snsService){
        this.repository = repository;
        this.snsService = snsService;
    }

    public Category insert(CategoryDTO categoryData){
        Category newCategory = new Category(categoryData);
        this.repository.save(newCategory);
        this.snsService.publish(new MessageDTO(newCategory.toString()));
        return newCategory;
    }

    public List<Category> getAll(){
        return this.repository.findAll();
    }

    public Category update(String categoryId, CategoryDTO categoryData){
        Category retrieveCategory = this.repository.findById(categoryId)
                .orElseThrow(CategoryNotFoundException::new);

        if(!categoryData.title().isEmpty()) retrieveCategory.setTitle(categoryData.title());
        if(!categoryData.description().isEmpty()) retrieveCategory.setDescription(categoryData.description());

        this.repository.save(retrieveCategory);
        this.snsService.publish(new MessageDTO(retrieveCategory.toString()));
        return retrieveCategory;
    }

    public void delete(String categoryId){
        Category retrieveCategory = this.repository.findById(categoryId)
                .orElseThrow(CategoryNotFoundException::new);

        this.repository.delete(retrieveCategory);
    }

    public Optional<Category> getBydId(String id){
        return this.repository.findById(id);
    }

}

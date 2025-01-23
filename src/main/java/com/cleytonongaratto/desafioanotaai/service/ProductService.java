package com.cleytonongaratto.desafioanotaai.service;

import com.cleytonongaratto.desafioanotaai.domain.category.Category;
import com.cleytonongaratto.desafioanotaai.domain.category.exceptions.CategoryNotFoundException;
import com.cleytonongaratto.desafioanotaai.domain.product.Product;
import com.cleytonongaratto.desafioanotaai.domain.product.ProductDTO;
import com.cleytonongaratto.desafioanotaai.domain.product.exceptions.ProductNotFoundException;
import com.cleytonongaratto.desafioanotaai.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private ProductRepository repository;
    private CategoryService categoryService;

    public ProductService(ProductRepository repository, CategoryService categoryService) {
        this.repository = repository;
        this.categoryService = categoryService;
    }

    public Product insert(ProductDTO productData) {

        Category category = this.categoryService.getBydId(productData.categoryId())
                .orElseThrow(CategoryNotFoundException::new);
        Product newProduct = new Product(productData);
        newProduct.setCategory(category);
        this.repository.save(newProduct);
        return newProduct;
    }

    public List<Product> getAll() {
        return this.repository.findAll();
    }

    public Product update(String productId, ProductDTO productData) {
        Product retrieveProduct = this.repository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);

        if (productData.categoryId() != null) {
            this.categoryService.getBydId(productData.categoryId())
                    .ifPresent(retrieveProduct::setCategory);
        }

        if (!productData.title().isEmpty()) retrieveProduct.setTitle(productData.title());
        if (!productData.description().isEmpty()) retrieveProduct.setDescription(productData.description());
        if (!(productData.price() == null)) retrieveProduct.setPrice(productData.price());


        this.repository.save(retrieveProduct);
        return retrieveProduct;
    }

    public void delete(String productId) {
        Product retrieveProduct = this.repository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);

        this.repository.delete(retrieveProduct);
    }


}

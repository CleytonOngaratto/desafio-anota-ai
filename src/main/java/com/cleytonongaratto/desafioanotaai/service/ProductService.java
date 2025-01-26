package com.cleytonongaratto.desafioanotaai.service;

import com.cleytonongaratto.desafioanotaai.domain.category.Category;
import com.cleytonongaratto.desafioanotaai.domain.category.exceptions.CategoryNotFoundException;
import com.cleytonongaratto.desafioanotaai.domain.product.Product;
import com.cleytonongaratto.desafioanotaai.domain.product.ProductDTO;
import com.cleytonongaratto.desafioanotaai.domain.product.exceptions.ProductNotFoundException;
import com.cleytonongaratto.desafioanotaai.repositories.ProductRepository;
import com.cleytonongaratto.desafioanotaai.service.aws.AwsSnsService;
import com.cleytonongaratto.desafioanotaai.service.aws.MessageDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final CategoryService categoryService;
    private final AwsSnsService snsService;

    public ProductService(ProductRepository repository, CategoryService categoryService, AwsSnsService snsService) {
        this.repository = repository;
        this.categoryService = categoryService;
        this.snsService = snsService;
    }

    public Product insert(ProductDTO productData) {

        this.categoryService.getBydId(productData.categoryId())
                .orElseThrow(CategoryNotFoundException::new);
        Product newProduct = new Product(productData);

        this.repository.save(newProduct);
        this.snsService.publish(new MessageDTO(newProduct.toString()));

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
                    .orElseThrow(ProductNotFoundException::new);
            retrieveProduct.setCategory(productData.categoryId());
        }

        if (!productData.title().isEmpty()) retrieveProduct.setTitle(productData.title());
        if (!productData.description().isEmpty()) retrieveProduct.setDescription(productData.description());
        if (!(productData.price() == null)) retrieveProduct.setPrice(productData.price());


        this.repository.save(retrieveProduct);
        this.snsService.publish(new MessageDTO(retrieveProduct.toString()));
        return retrieveProduct;
    }

    public void delete(String productId) {
        Product retrieveProduct = this.repository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);

        this.repository.delete(retrieveProduct);
    }


}

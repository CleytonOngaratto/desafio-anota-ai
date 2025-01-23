package com.cleytonongaratto.desafioanotaai.controllers;

import com.cleytonongaratto.desafioanotaai.domain.product.Product;
import com.cleytonongaratto.desafioanotaai.domain.product.ProductDTO;
import com.cleytonongaratto.desafioanotaai.repositories.ProductRepository;
import com.cleytonongaratto.desafioanotaai.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private ProductService service;

    public ProductController(ProductService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Product> insert(@RequestBody ProductDTO productData){
        Product newProduct = this.service.insert(productData);
        return ResponseEntity.ok().body(newProduct);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAll(){
        List<Product> categories = this.service.getAll();
        return ResponseEntity.ok().body(categories);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> update(@PathVariable("productId") String productId, @RequestBody ProductDTO productData){
        Product updatedProduct = this.service.update(productId, productData);
        return ResponseEntity.ok().body(updatedProduct);

    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Product> delete(@PathVariable("productId") String productId){
        this.service.delete(productId);
        return ResponseEntity.noContent().build();
    }
}

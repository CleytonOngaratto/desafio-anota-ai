package com.cleytonongaratto.desafioanotaai.repositories;

import com.cleytonongaratto.desafioanotaai.domain.category.Category;
import com.cleytonongaratto.desafioanotaai.domain.products.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
}

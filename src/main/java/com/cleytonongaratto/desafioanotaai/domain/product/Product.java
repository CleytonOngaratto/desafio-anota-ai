package com.cleytonongaratto.desafioanotaai.domain.product;

import com.cleytonongaratto.desafioanotaai.domain.category.Category;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
public class Product {

    @Id
    private String id;
    private String title;
    private String description;
    private String ownerId;
    private Integer price;
    private Category category;

    public Product() {
    }

    public Product(String id, String title, String description, String ownerId, Integer price, Category category) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.ownerId = ownerId;
        this.price = price;
        this.category = category;
    }

    public Product(ProductDTO productData) {
        this.title = productData.title();
        this.description = productData.description();
        this.ownerId = productData.ownerId();
        this.price = productData.price();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }
}

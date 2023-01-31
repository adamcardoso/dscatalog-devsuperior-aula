package com.devsuperior.dscatalog.tests;

import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;

import java.time.Instant;

public class Factory {
    public static Product createProduct() {
        Product product = new Product(null, "Phone", "Good Phone",
                800.0, "https://img.com/img.png", Instant.parse("2021-10-20T03:00:00Z"));
        product.getCategories().add(new Category(2L, null));

        return product;
    }

    public static ProductDTO createProductDTO() {
        Product product = createProduct();

        return new ProductDTO(product, product.getCategories());
    }
}

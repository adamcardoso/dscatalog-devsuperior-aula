package com.devsuperior.dscatalog.dto;

import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@NoArgsConstructor
public class ProductDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;
    @Size(min = 3, max = 60, message = "The size must be between 5 and 60 characters")
    @NotEmpty(message = "Can't be empty")
    @Getter @Setter private String name;

    @NotBlank(message = "Can't be empty")
    @Column(columnDefinition = "TEXT")
    @Getter @Setter private String description;

    @Positive(message = "The price must be a positive value")
    @Getter @Setter private Double price;
    @Getter @Setter private String imgUrl;

    @PastOrPresent(message = "The date can't be in the future")
    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    @Getter @Setter private Instant date;

    @Getter @Setter private List<CategoryDTO> categoriesDTO = new ArrayList<>();

    public ProductDTO(Long id, String name, String description, Double price, String imgUrl, Instant date) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
        this.date = date;
    }

    public ProductDTO(Product entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.price = entity.getPrice();
        this.imgUrl = entity.getImgUrl();
        this.date = entity.getDate();
    }

    public ProductDTO(Product entity, Set<Category> categories) {
        this(entity);
        categories.forEach(cat -> this.categoriesDTO.add(new CategoryDTO(cat)));
    }
}

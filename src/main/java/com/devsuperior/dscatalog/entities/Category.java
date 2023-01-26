package com.devsuperior.dscatalog.entities;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Entity // This annotation is used to indicate that this class is an entity
@Table(name = "tb_category") // This annotation is used to indicate the name of the table in the database
public class Category implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id  // This annotation is used to indicate that this attribute is the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // This annotation is used to indicate that the primary key is auto-increment
    @Getter @Setter private Long id;
    @Getter @Setter private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        return Objects.equals(id, category.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}

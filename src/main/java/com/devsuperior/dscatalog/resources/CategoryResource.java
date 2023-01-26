package com.devsuperior.dscatalog.resources;

import com.devsuperior.dscatalog.entities.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController // This annotation is used to indicate that this class is a REST controller
@RequestMapping(value = "/categories") // This annotation is used to indicate the route of the resource
public class CategoryResource {
    // creating the first end point

    @GetMapping // This annotation is used to indicate that this method is a GET method
    public ResponseEntity<List<Category>> findAll(){  // ResponseEntity is a type that allows to return responses from web services
        List<Category> list = new ArrayList<>(); // creating a list of categories

        list.add(new Category(1L, "Books")); // adding categories to the list
        list.add(new Category(2L, "Electronics")); // adding categories to the list

        return ResponseEntity.ok().body(list); // returning the list of categories
    }
}

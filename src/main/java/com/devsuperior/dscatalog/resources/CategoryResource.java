package com.devsuperior.dscatalog.resources;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController // This annotation is used to indicate that this class is a REST controller
@RequestMapping(value = "/categories") // This annotation is used to indicate the route of the resource
public class CategoryResource {

    @Autowired
    private CategoryService categoryService;

    // creating the first end point
    @GetMapping // This annotation is used to indicate that this method is a GET method
    public ResponseEntity<List<CategoryDTO>> findAll(){  // ResponseEntity is a type that allows to return responses from web services
        List<CategoryDTO> list = categoryService.findAll(); // calling the findAll method from the service

        return ResponseEntity.ok().body(list); // returning the list of categories
    }
}

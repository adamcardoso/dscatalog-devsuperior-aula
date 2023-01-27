package com.devsuperior.dscatalog.resources;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable Long id){
        CategoryDTO dto = categoryService.findById(id);

        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> insert(@RequestBody CategoryDTO categoryDTO){
        categoryDTO = categoryService.insert(categoryDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(categoryDTO.getId()).toUri(); // This line is used to return the URI of the new resource created

        return ResponseEntity.created(uri).body(categoryDTO); // returning the list of categories
    }

    @PutMapping(value = "/{id}")
    public  ResponseEntity<CategoryDTO> update(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO){
        categoryDTO = categoryService.update(id, categoryDTO);

        return ResponseEntity.ok().body(categoryDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        categoryService.delete(id);

        return ResponseEntity.noContent().build();
    }
}

package com.devsuperior.dscatalog.resources;


import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.dto.UserDTO;
import com.devsuperior.dscatalog.dto.UserInsertDTO;
import com.devsuperior.dscatalog.dto.UserUpdateDTO;
import com.devsuperior.dscatalog.services.ProductService;
import com.devsuperior.dscatalog.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController // This annotation is used to indicate that this class is a REST controller
@RequestMapping(value = "/users") // This annotation is used to indicate the route of the resource
public class UsersResource {

    @Autowired
    private UserService userService;

    // creating the first end point
    @GetMapping // This annotation is used to indicate that this method is a GET method
    public ResponseEntity<Page<UserDTO>> findAll(Pageable pageable){  // ResponseEntity is a type that allows to return responses from web services
        Page<UserDTO> list = userService.findAllPaged(pageable); // calling the findAll method from the service

        return ResponseEntity.ok().body(list); // returning the list of categories
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id){
        UserDTO dto = userService.findById(id);

        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<UserDTO> insert(@Valid @RequestBody UserInsertDTO dto) {
        UserDTO newDto = userService.insert(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newDto.getId()).toUri();
        return ResponseEntity.created(uri).body(newDto);
    }

    @PutMapping(value = "/{id}")
    public  ResponseEntity<UserDTO> update(@Valid @PathVariable Long id, @RequestBody UserUpdateDTO userDTO){
        UserDTO newDto = userService.update(id, userDTO);

        return ResponseEntity.ok().body(userDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        userService.delete(id);

        return ResponseEntity.noContent().build();
    }
}

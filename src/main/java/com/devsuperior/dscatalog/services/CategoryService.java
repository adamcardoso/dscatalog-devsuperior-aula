package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;

import com.devsuperior.dscatalog.services.expections.DatabaseException;
import com.devsuperior.dscatalog.services.expections.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service //Service is a component that is used to implement business rules
public class CategoryService {

    @Autowired //Dependency injection
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true) // This annotation is used to indicate that the method is a transactional method
    public List<CategoryDTO> findAll() {
        List<Category> list = categoryRepository.findAll();

        //return list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
        return list.stream().map(CategoryDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        Optional<Category> obj = categoryRepository.findById(id);
        Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Error! Entity not found"));

        return new CategoryDTO(entity);
    }

    @Transactional
    public CategoryDTO insert(CategoryDTO categoryDTO) {
        Category entity = new Category();
        entity.setName(categoryDTO.getName());
        entity = categoryRepository.save(entity);

        return new CategoryDTO(entity);
    }

    @Transactional
    public CategoryDTO update(Long id, CategoryDTO categoryDTO) {
        try {
            Optional<Category> optionalEntity = categoryRepository.findById(id);
            if(optionalEntity.isPresent()) {
                Category entity = optionalEntity.get();
                entity.setName(categoryDTO.getName());
                entity = categoryRepository.save(entity);
                return new CategoryDTO(entity);
            } else {
                throw new ResourceNotFoundException("Error! Id not found " + id);
            }
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Error! Id not found " + id);
        }
    }

    public void delete(Long id) {
        try {
            categoryRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Error! Id not found " + id);
        }catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Error! Integrity violation");
        }
    }
}

package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service //Service is a component that is used to implement business rules
public class CategoryService {

    @Autowired //Dependency injection
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true) // This annotation is used to indicate that the method is a transactional method
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}

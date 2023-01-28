package com.devsuperior.dscatalog.services;



import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.expections.DatabaseException;
import com.devsuperior.dscatalog.services.expections.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service //Service is a component that is used to implement business rules
public class ProductService {

    @Autowired //Dependency injection
    private ProductRepository productRepository;

    @Transactional(readOnly = true) // This annotation is used to indicate that the method is a transactional method
    public Page<ProductDTO> findALlPaged(PageRequest pageRequest) {
        Page<Product> list = productRepository.findAll(pageRequest);

        return list.map(ProductDTO::new);
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Optional<Product> obj = productRepository.findById(id);
        Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Error! Entity not found"));

        return new ProductDTO(entity, entity.getCategories());
    }

    @Transactional
    public ProductDTO insert(ProductDTO productDTO) {
        Product entity = new Product();
        entity.setName(productDTO.getName());
        entity = productRepository.save(entity);

        return new ProductDTO(entity);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO productDTO) {
        try {
            Optional<Product> optionalEntity = productRepository.findById(id);
            if(optionalEntity.isPresent()) {
                Product entity = optionalEntity.get();
                entity.setName(productDTO.getName());
                entity = productRepository.save(entity);

                return new ProductDTO(entity);
            } else {
                throw new ResourceNotFoundException("Error! Id not found " + id);
            }
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Error! Id not found " + id);
        }
    }

    public void delete(Long id) {
        try {
            productRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Error! Id not found " + id);
        }catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Error! Integrity violation");
        }
    }
}

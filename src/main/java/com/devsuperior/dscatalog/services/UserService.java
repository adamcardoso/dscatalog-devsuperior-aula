package com.devsuperior.dscatalog.services;



import java.util.Objects;
import java.util.Optional;

import com.devsuperior.dscatalog.dto.RoleDTO;
import com.devsuperior.dscatalog.dto.UserInsertDTO;
import com.devsuperior.dscatalog.dto.UserUpdateDTO;
import com.devsuperior.dscatalog.repositories.RoleRepository;
import com.devsuperior.dscatalog.services.expections.DatabaseException;
import com.devsuperior.dscatalog.services.expections.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.UserDTO;
import com.devsuperior.dscatalog.entities.User;
import com.devsuperior.dscatalog.repositories.UserRepository;

@Service //Service is a component that is used to implement business rules
public class UserService implements UserDetailsService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public Page<UserDTO> findAllPaged(Pageable pageable) {
        Page<User> list = userRepository.findAll(pageable);
        return list.map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        Optional<User> obj = userRepository.findById(id);
        User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Error! Entity not found"));

        return new UserDTO(entity);
    }

    @Transactional
    public UserDTO insert(UserInsertDTO userInsertDTO) {
        User entity = new User();

        copyDtoToEntity(userInsertDTO, entity);
        entity.setPassword(passwordEncoder.encode(userInsertDTO.getPassword())); //encrypting the password

        entity = userRepository.save(entity);

        return new UserDTO(entity);
    }

    @Transactional
    public UserDTO update(Long id, UserUpdateDTO userDTO) {
        try {
            Optional<User> optionalEntity = userRepository.findById(id);
            if(optionalEntity.isPresent()) {
                User entity = optionalEntity.get();
                copyDtoToEntity(userDTO, entity);
                entity = userRepository.save(entity);

                return new UserDTO(entity);
            } else {
                throw new ResourceNotFoundException("Error! Id not found " + id);
            }
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Error! Id not found " + id);
        }
    }

    public void delete(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Error! Id not found " + id);
        }catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Error! Integrity violation");
        }
    }

    private void copyDtoToEntity(UserDTO userDTO, User entity) {
        entity.setFirstName(userDTO.getFirstName());
        entity.setLastName(userDTO.getLastName());
        entity.setEmail(userDTO.getEmail());

        entity.getRoles().clear();

        for (RoleDTO roleDto : userDTO.getRoles()) {
            roleRepository.findById(roleDto.getId()).ifPresent(role -> entity.getRoles().add(role)); //ifPresent is a method of Optional
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (Objects.isNull(user)){
            logger.error("User not found: " + username);
            throw new UsernameNotFoundException("Email not found");
        }
        logger.info("User found: " + username);

        return user;
    }
}

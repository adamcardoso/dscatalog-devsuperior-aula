package com.devsuperior.dscatalog.dto;

import com.devsuperior.dscatalog.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Getter @Setter private Long id;
    @Getter @Setter
    private String authority;

    public RoleDTO(Role role) {
        id = role.getId();
        authority = role.getAuthority();
    }
}

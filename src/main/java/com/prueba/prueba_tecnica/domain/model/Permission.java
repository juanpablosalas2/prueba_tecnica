package com.prueba.prueba_tecnica.domain.model;


import com.prueba.prueba_tecnica.infraestructure.driven_adapters.repositories.entities.AlbumEntity;
import com.prueba.prueba_tecnica.infraestructure.driven_adapters.repositories.entities.UserEntity;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder(toBuilder = true)
@Data
public class Permission {

    private Long id;

    @NotNull(message = "El tipo de permiso  no puede ser null")
    @NotEmpty(message = "El tipo de permiso  no puede estar vacio")
    private String typePermission;

    @NotNull(message = "El album  no puede ser null")
    @NotEmpty(message = "El album  no puede estar vacio")
    private Album album;

    @NotNull(message = "El user  no puede ser null")
    @NotEmpty(message = "El user  no puede estar vacio")
    private User user;
}

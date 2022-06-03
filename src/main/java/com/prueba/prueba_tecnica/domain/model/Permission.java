package com.prueba.prueba_tecnica.domain.model;


import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Builder(toBuilder = true)
@Data
public class Permission implements Serializable {

    private Long id;

    @NotNull(message = "El tipo de permiso  no puede ser null")
    @NotEmpty(message = "El tipo de permiso  no puede estar vacio")
    private String typePermission;

    @NotNull(message = "El album  no puede ser null")
    private Long album;

    @NotNull(message = "El user  no puede ser null")
    private Long user;
}

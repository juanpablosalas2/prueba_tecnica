package com.prueba.prueba_tecnica.domain.model;


import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder(toBuilder = true)
public class Comment {

    private Long id;


    @NotNull(message = "El nombre  no puede ser null")
    @NotEmpty(message = "El nombre  no puede estar vacio")
    private String name;


    @NotNull(message = "El email  no puede ser null")
    @NotEmpty(message = "El email  no puede estar vacio")
    @Email
    private String email;


    @NotNull(message = "El cuerpo  no puede ser null")
    @NotEmpty(message = "El cuerpo  no puede estar vacio")
    private String body;
}


package com.prueba.prueba_tecnica.domain.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder(toBuilder = true)
public class Comment implements Serializable {

    @JsonProperty
    private Long id;


    @NotNull(message = "El nombre  no puede ser null")
    @NotEmpty(message = "El nombre  no puede estar vacio")
    @JsonProperty
    private String name;


    @NotNull(message = "El email  no puede ser null")
    @NotEmpty(message = "El email  no puede estar vacio")
    @Email
    @JsonProperty
    private String email;


    @NotNull(message = "El cuerpo  no puede ser null")
    @NotEmpty(message = "El cuerpo  no puede estar vacio")
    @JsonProperty
    private String body;
}


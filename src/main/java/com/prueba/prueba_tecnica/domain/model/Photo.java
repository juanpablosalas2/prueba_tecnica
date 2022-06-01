package com.prueba.prueba_tecnica.domain.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder(toBuilder = true)
public class Photo {
    private Long id;

    @NotNull(message = "El titulo  no puede ser null")
    @NotEmpty(message = "El titulo  no puede estar vacio")
    private String title;

    @NotNull(message = "la url  no puede ser null")
    @NotEmpty(message = "la url  no puede estar vacio")
    private String url;

    @NotNull(message = "El album  no puede ser null")
    private Long album;
}

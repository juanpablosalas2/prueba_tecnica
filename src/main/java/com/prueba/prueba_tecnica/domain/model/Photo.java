package com.prueba.prueba_tecnica.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder(toBuilder = true)
public class Photo implements Serializable {
    @JsonProperty
    private Long id;

    @NotNull(message = "El titulo  no puede ser null")
    @NotEmpty(message = "El titulo  no puede estar vacio")
    @JsonProperty
    private String title;

    @NotNull(message = "la url  no puede ser null")
    @NotEmpty(message = "la url  no puede estar vacio")
    @JsonProperty
    private String url;

    @NotNull(message = "El album  no puede ser null")
    @JsonProperty("albumId")
    private Long album;
}

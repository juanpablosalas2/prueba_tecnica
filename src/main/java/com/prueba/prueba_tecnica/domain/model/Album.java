package com.prueba.prueba_tecnica.domain.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Flux;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Album implements Serializable {
    @JsonProperty
    private Long id;

    @NotNull(message = "El titulo  no puede ser null")
    @NotEmpty(message = "El titulo  no puede estar vacio")
    @JsonProperty
    private String title;

    @NotNull(message = "El user  no puede ser null")
    @JsonProperty("userId")
    private Long userCreate;

    private List<Permission> permissions;


    private List<Photo> photos;
}

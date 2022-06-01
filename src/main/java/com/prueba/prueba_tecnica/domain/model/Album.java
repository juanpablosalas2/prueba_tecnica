package com.prueba.prueba_tecnica.domain.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Flux;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Album {
    
    private Long id;

    @NotNull(message = "El titulo  no puede ser null")
    @NotEmpty(message = "El titulo  no puede estar vacio")
    private String title;

    @NotNull(message = "El user  no puede ser null")
    @NotEmpty(message = "El user  no puede estar vacio")
    private User user;


    private Flux<Permission> permissions;


    private Flux<Photo> photos;
}

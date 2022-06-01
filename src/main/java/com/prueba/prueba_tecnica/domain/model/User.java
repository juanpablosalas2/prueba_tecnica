package com.prueba.prueba_tecnica.domain.model;


import lombok.Builder;
import lombok.Data;
import reactor.core.publisher.Flux;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder(toBuilder = true)
public class User {

    private Long id;

    @NotNull(message = "El nombre  no puede ser null")
    @NotEmpty(message = "El nombre  no puede estar vacio")
    private String name;

    @NotNull(message = "El email  no puede ser null")
    @NotEmpty(message = "El email  no puede estar vacio")
    @Email
    private String email;

    @NotNull(message = "El email  no puede ser null")
    @NotEmpty(message = "El email  no puede estar vacio")
    private String phone;

    @NotNull(message = "El sitio web  no puede ser null")
    @NotEmpty(message = "El sitio web  no puede estar vacio")
    private String website;

    private Flux<Album> albums;

    private Flux<Permission> permissions;
}

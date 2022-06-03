package com.prueba.prueba_tecnica.domain.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import reactor.core.publisher.Flux;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@Builder(toBuilder = true)
public class User implements Serializable {

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

    @NotNull(message = "El email  no puede ser null")
    @NotEmpty(message = "El email  no puede estar vacio")
    @JsonProperty
    private String phone;

    @NotNull(message = "El sitio web  no puede ser null")
    @NotEmpty(message = "El sitio web  no puede estar vacio")
    @JsonProperty
    private String website;

    private List<Album> albums;

    private List<Permission> permissions;
}

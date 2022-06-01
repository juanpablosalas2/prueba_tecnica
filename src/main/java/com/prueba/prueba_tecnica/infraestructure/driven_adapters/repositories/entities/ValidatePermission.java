package com.prueba.prueba_tecnica.infraestructure.driven_adapters.repositories.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class ValidatePermission {

    private Long idUser;

    private Long idAlbum;
}

package com.prueba.prueba_tecnica.domain.usecase.interfaces;

import com.prueba.prueba_tecnica.domain.model.Album;
import com.prueba.prueba_tecnica.domain.model.Permission;
import reactor.core.publisher.Flux;

@FunctionalInterface
public interface GetAllUsersOfAlbumAccordingToPermission {

    Flux<Permission> process(String permission, Long albumId);
}

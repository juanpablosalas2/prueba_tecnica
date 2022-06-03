package com.prueba.prueba_tecnica.domain.usecase.interfaces;

import com.prueba.prueba_tecnica.domain.model.Permission;
import reactor.core.publisher.Mono;

public interface PermissionUseCase {

    Mono<Permission> addPermissionOfAlbumToUser(Permission permission);

    Mono<Permission> changePermissionOfUserInAlbum(Permission permission);

    Mono<Void> deletePermission(Long idPermission);
}

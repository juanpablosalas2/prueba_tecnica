package com.prueba.prueba_tecnica.domain.usecase.interfaces;

import reactor.core.publisher.Mono;

public interface ValidatePermissionUser {

    Mono<Boolean> validatePermissionUser(Long UserId, Long AlbumId);
}

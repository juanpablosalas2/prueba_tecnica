package com.prueba.prueba_tecnica.domain.usecase;

import com.prueba.prueba_tecnica.domain.model.Permission;
import com.prueba.prueba_tecnica.domain.model.exceptions.AlbumException;
import com.prueba.prueba_tecnica.domain.model.exceptions.PermissionException;
import com.prueba.prueba_tecnica.domain.model.exceptions.PhotoException;
import com.prueba.prueba_tecnica.domain.usecase.interfaces.GetAllUsersOfAlbumAccordingToPermission;
import com.prueba.prueba_tecnica.infraestructure.driven_adapters.repositories.AlbumRepository;
import com.prueba.prueba_tecnica.infraestructure.driven_adapters.repositories.PermissionRepository;
import com.prueba.prueba_tecnica.infraestructure.driven_adapters.repositories.entities.AlbumEntity;
import com.prueba.prueba_tecnica.infraestructure.driven_adapters.repositories.entities.PermissionEntity;
import com.prueba.prueba_tecnica.infraestructure.driven_adapters.repositories.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GetAllUsersOfAlbumAccordingToPermissionImp implements GetAllUsersOfAlbumAccordingToPermission {


    private static final String ID_NOT_FOUND = "Album With ID %d not found";
    private final PermissionRepository permissionRepository;

    @Override
    public Flux<Permission> process(String permission, Long albumId) {
        return Flux.fromIterable(permissionRepository.findAllByAlbumEntity_IdAndTypePermission(albumId,permission))
                .switchIfEmpty(Mono.error(new AlbumException(String.format(ID_NOT_FOUND, albumId))))
                .map(PermissionEntity::convertToModel)
                .onErrorResume(throwable -> Flux.error(new PermissionException("No se pudo filtrar por los valores, " + throwable.getMessage())));
    }
}

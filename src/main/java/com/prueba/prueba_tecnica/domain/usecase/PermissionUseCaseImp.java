package com.prueba.prueba_tecnica.domain.usecase;

import com.prueba.prueba_tecnica.domain.model.Permission;
import com.prueba.prueba_tecnica.domain.model.exceptions.PermissionException;
import com.prueba.prueba_tecnica.domain.model.exceptions.PhotoException;
import com.prueba.prueba_tecnica.domain.model.exceptions.UserException;
import com.prueba.prueba_tecnica.domain.usecase.interfaces.PermissionUseCase;
import com.prueba.prueba_tecnica.infraestructure.driven_adapters.repositories.AlbumRepository;
import com.prueba.prueba_tecnica.infraestructure.driven_adapters.repositories.PermissionRepository;
import com.prueba.prueba_tecnica.infraestructure.driven_adapters.repositories.UserRepository;
import com.prueba.prueba_tecnica.infraestructure.driven_adapters.repositories.entities.PermissionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PermissionUseCaseImp implements PermissionUseCase {

    private static final String ID_NOT_FOUND = "Permission With ID %d not found";
    private final PermissionRepository permissionRepository;
    private final UserRepository userRepository;

    private final AlbumRepository albumRepository;

    @Override
    public Mono<Permission> addPermissionOfAlbumToUser(Permission permission) {
        return existAlbumAndUser(permission)
                .map(existAlbum -> Boolean.TRUE.equals(existAlbum) ? PermissionEntity.convertToEntity(permission) : null )
                .map(permissionRepository::save)
                .map(PermissionEntity::convertToModel)
                .onErrorResume(throwable -> Mono.error(new PermissionException("Permission Could not be saved ")));
    }

    private Mono<Boolean> existAlbumAndUser(Permission permission) {
        return Mono.just(permission.getUser())
                .map(userRepository::existsById)
                .map(exist -> Boolean.TRUE.equals(exist) ? albumRepository.existsById(permission.getAlbum()) : null);
    }

    @Override
    public Mono<Permission> changePermissionOfUserInAlbum(Permission permission) {
        return existAlbumAndUser(permission)
                .map(existAlbum -> Boolean.TRUE.equals(existAlbum) ? PermissionEntity.convertToEntity(permission) : null )
                .map(permissionEntity -> permissionRepository.findById(permissionEntity.getId()))
                .switchIfEmpty(Mono.error(new PermissionException(String.format(ID_NOT_FOUND,permission.getId()))))
                .map(permissionEntity -> {
                    PermissionEntity newUser = PermissionEntity.builder()
                            .id(permissionEntity.get().getId())
                            .typePermission(permission.getTypePermission())
                            .albumEntity(permissionEntity.get().getAlbumEntity())
                            .userEntity(permissionEntity.get().getUserEntity())
                            .build();

                    return permissionRepository.save(newUser);
                })
                .map(PermissionEntity::convertToModel)
                .onErrorResume(throwable -> Mono.error(new PermissionException("Permission Could not be Update")));
    }

    @Override
    public Mono<Void> deletePermission(Long idPermission) {
        return Mono.just(idPermission)
                .map(permissionRepository::existsById)
                .switchIfEmpty(Mono.error(new UserException(String.format(ID_NOT_FOUND,idPermission))))
                .map(exist -> Boolean.TRUE.equals(exist) ? idPermission : null)
                .flatMap(aLong -> {
                    permissionRepository.deleteById(aLong);
                    return Mono.when();
                })
                .onErrorResume(throwable -> Mono.error(new PermissionException("Permission could not be deleted",throwable)));
    }
}

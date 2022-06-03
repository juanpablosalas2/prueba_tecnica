package com.prueba.prueba_tecnica.domain.usecase;

import com.prueba.prueba_tecnica.domain.model.Album;
import com.prueba.prueba_tecnica.domain.model.Permission;
import com.prueba.prueba_tecnica.domain.model.exceptions.AlbumException;
import com.prueba.prueba_tecnica.domain.model.exceptions.PhotoException;
import com.prueba.prueba_tecnica.domain.usecase.interfaces.AlbumUseCase;
import com.prueba.prueba_tecnica.domain.usecase.interfaces.ValidatePermissionUser;
import com.prueba.prueba_tecnica.infraestructure.driven_adapters.repositories.AlbumRepository;
import com.prueba.prueba_tecnica.infraestructure.driven_adapters.repositories.PermissionRepository;
import com.prueba.prueba_tecnica.infraestructure.driven_adapters.repositories.PhotoRepository;
import com.prueba.prueba_tecnica.infraestructure.driven_adapters.repositories.entities.AlbumEntity;
import com.prueba.prueba_tecnica.infraestructure.driven_adapters.repositories.entities.PermissionEntity;
import com.prueba.prueba_tecnica.infraestructure.driven_adapters.repositories.entities.PhotoEntity;
import com.prueba.prueba_tecnica.infraestructure.driven_adapters.repositories.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlbumUseCaseImpl implements AlbumUseCase {

    private static final String ID_NOT_FOUND = "Album With ID %d not found";
    public static final String WRITE = "write";
    private final AlbumRepository albumRepository;
    private final ValidatePermissionUser validatePermissionUser;
    private final PermissionRepository permissionRepository;

    private final PhotoRepository photoRepository;

    @Override
    public Mono<Album> createAlbum(Album album) {
        return Mono.just(album)
                .map(AlbumEntity::convertToEntity)
                .map(albumRepository::save)
                .map(albumEntity -> {
                    PermissionEntity permission = PermissionEntity.builder()
                            .albumEntity(AlbumEntity.builder().id(albumEntity.getId()).build())
                            .userEntity(UserEntity.builder().id(albumEntity.getUserCreate()).build())
                            .typePermission(WRITE)
                            .build();
                    permissionRepository.save(permission);
                    return AlbumEntity.convertToModel(albumEntity);
                })
                .onErrorResume(throwable -> Mono.error(new AlbumException("Album could not be saved" + throwable.getMessage(), throwable)));
    }

    @Override
    public Mono<Void> deleteAlbum(Long idAlbum, Long idUser) {
        return Mono.just(idAlbum)
                .map(albumRepository::findById)
                .switchIfEmpty(Mono.error(new PhotoException(String.format(ID_NOT_FOUND, idAlbum))))
                .flatMap(photoEntity -> validatePermissionUser.validatePermissionUser(idUser, idAlbum))
                .map(isWrite -> Boolean.TRUE.equals(isWrite) ? idAlbum : null)
                .flatMap(aLong -> {
                    albumRepository.deleteById(aLong);
                    return Mono.when();
                })
                .onErrorResume(throwable -> Mono.error(new AlbumException("Album could not be deleted,verify permissions", throwable)));
    }

    @Override
    public Mono<Album> updateAlbum(Album album) {
        return verifyPermissions(album)
                .map(albumEntity -> albumRepository.findById(albumEntity.getId()))
                .switchIfEmpty(Mono.error(new PhotoException(String.format(ID_NOT_FOUND, album.getId()))))
                .map(albumEntity -> {
                    AlbumEntity newPhoto = AlbumEntity.builder()
                            .id(albumEntity.get().getId())
                            .title(album.getTitle())
                            .userCreate(album.getUserCreate())
                            .build();
                    return albumRepository.save(newPhoto);
                })
                .map(AlbumEntity::convertToModel)
                .onErrorResume(throwable -> Mono.error(new AlbumException("Album could not be update,verify permissions", throwable)));
    }

    @Override
    public Flux<Album> getAlbumsByUserId(Long idUser) {
        return Flux.fromIterable(albumRepository.findAllByUserCreate(idUser))
                .map(AlbumEntity::convertToModel)
                .map(album -> {
                    album.setPermissions(permissionRepository.findAllByUserEntity_Id(album.getUserCreate()).stream()
                            .map(PermissionEntity::convertToModel).collect(Collectors.toList()));
                    album.setPhotos(photoRepository.findAllByAlbumEntity_Id(album.getId()).stream()
                            .map(PhotoEntity::convertToModel).collect(Collectors.toList()));
                    return album;
                })
                .onErrorResume(throwable -> Mono.error(new AlbumException("Album could not be find,verify permissions", throwable)));
    }

    @Override
    public Flux<Album> getAllAlbums() {
        return Flux.fromIterable(albumRepository.findAll())
                .map(AlbumEntity::convertToModel)
                .map(album -> {
                    album.setPermissions(permissionRepository.findAllByAlbumEntity_Id(album.getId()).stream()
                            .map(PermissionEntity::convertToModel).collect(Collectors.toList()));
                    album.setPhotos(photoRepository.findAllByAlbumEntity_Id(album.getId()).stream()
                            .map(PhotoEntity::convertToModel).collect(Collectors.toList()));
                    return album;
                })
                .switchIfEmpty(Mono.error(new PhotoException("Albums not found")));
    }


    private Mono<AlbumEntity> verifyPermissions(Album album) {
        return Mono.just(album.getUserCreate())
                .flatMap(id -> validatePermissionUser.validatePermissionUser(id, album.getId()))
                .map(isWrite -> Boolean.TRUE.equals(isWrite) ? AlbumEntity.convertToEntity(album) : AlbumEntity.builder().build())
                .onErrorStop();
    }
}

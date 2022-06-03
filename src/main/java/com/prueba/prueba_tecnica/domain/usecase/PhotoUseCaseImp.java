package com.prueba.prueba_tecnica.domain.usecase;

import com.prueba.prueba_tecnica.domain.model.Photo;
import com.prueba.prueba_tecnica.domain.model.exceptions.PhotoException;
import com.prueba.prueba_tecnica.domain.usecase.interfaces.PhotoUseCase;
import com.prueba.prueba_tecnica.domain.usecase.interfaces.ValidatePermissionUser;
import com.prueba.prueba_tecnica.infraestructure.driven_adapters.repositories.PhotoRepository;
import com.prueba.prueba_tecnica.infraestructure.driven_adapters.repositories.entities.AlbumEntity;
import com.prueba.prueba_tecnica.infraestructure.driven_adapters.repositories.entities.PhotoEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class PhotoUseCaseImp implements PhotoUseCase {
    private static final String ID_NOT_FOUND = "Photo With ID %d not found";
    private final PhotoRepository photoRepository;
    private final ValidatePermissionUser validatePermissionUser;

    @Override
    public Mono<Photo> createPhoto(Photo photo, Long idUser) {
        return verifyPermissions(photo, idUser)
                .map(photoRepository::save)
                .map(photoEntity -> PhotoEntity.convertToModel(photoEntity))
                .onErrorResume(throwable -> Mono.error(new PhotoException("Photo could not be saved,verify permissions", throwable)));
    }


    @Override
    public Mono<Photo> updatePhoto(Photo photo, Long idUser) {
        return verifyPermissions(photo, idUser)
                .map(photoEntity -> photoRepository.findById(photoEntity.getId()))
                .switchIfEmpty(Mono.error(new PhotoException(String.format(ID_NOT_FOUND,photo.getId()))))
                .map(photoEntity -> {
                    PhotoEntity newPhoto = PhotoEntity.builder()
                            .id(photoEntity.get().getId())
                            .title(photo.getTitle())
                            .url(photo.getUrl())
                            .albumEntity(AlbumEntity.builder().id(photo.getAlbum()).build())
                            .build();
                    return photoRepository.save(newPhoto);
                })
                .map(PhotoEntity::convertToModel)
                .onErrorResume(throwable -> Mono.error(new PhotoException("Photo could not be update,verify permissions", throwable)));
    }

    @Override
    public Mono<Void> deletePhoto(Long idPhoto, Long idUser) {
        return Mono.just(idPhoto)
                .map(photoRepository::findById)
                .switchIfEmpty(Mono.error(new PhotoException(String.format(ID_NOT_FOUND,idPhoto))))
                .flatMap(photoEntity -> validatePermissionUser.validatePermissionUser(idUser, photoEntity.get().getAlbumEntity().getId()))
                .map(isWrite -> Boolean.TRUE.equals(isWrite) ? idPhoto : null)
                .flatMap(aLong -> {
                    photoRepository.deleteById(aLong);
                    return Mono.when();
                })
                .onErrorResume(throwable -> Mono.error(new PhotoException("Photo could not be deleted,verify permissions",throwable)));
    }

    @Override
    public Flux<Photo> findAllPhotos() {
        return Flux.fromIterable(photoRepository.findAll())
                .map(PhotoEntity::convertToModel)
                .onErrorResume(throwable -> Mono.error(new PhotoException("Photo could not be founded",throwable)));
    }




    private Mono<PhotoEntity> verifyPermissions(Photo photo, Long idUser) {
        return Mono.just(idUser)
                .flatMap(id -> validatePermissionUser.validatePermissionUser(id, photo.getAlbum()))
                .map(isWrite -> Boolean.TRUE.equals(isWrite) ? PhotoEntity.convertToEntity(photo) :null)
                .onErrorStop();
    }

}

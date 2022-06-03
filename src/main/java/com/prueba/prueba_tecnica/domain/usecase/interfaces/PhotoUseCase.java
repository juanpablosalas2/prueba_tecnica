package com.prueba.prueba_tecnica.domain.usecase.interfaces;

import com.prueba.prueba_tecnica.domain.model.Photo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PhotoUseCase {

    Mono<Photo> createPhoto(Photo photo, Long idUser);

    Mono<Void> deletePhoto(Long idPhoto, Long idUser);

    Mono<Photo> updatePhoto(Photo photo, Long idUser);

    Flux<Photo> findAllPhotos();

}

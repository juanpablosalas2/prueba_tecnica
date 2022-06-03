package com.prueba.prueba_tecnica.domain.usecase.interfaces;

import com.prueba.prueba_tecnica.domain.model.Album;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AlbumUseCase {

    Mono<Album> createAlbum(Album album);

    Mono<Void> deleteAlbum(Long idAlbum,Long idUser);

    Mono<Album> updateAlbum(Album album);

    Flux<Album> getAlbumsByUserId(Long idUser);

    Flux<Album> getAllAlbums();


}

package com.prueba.prueba_tecnica.infraestructure.entry_points.reactive_web;


import com.prueba.prueba_tecnica.domain.model.Album;
import com.prueba.prueba_tecnica.domain.model.GeneralResponse;
import com.prueba.prueba_tecnica.domain.model.Photo;
import com.prueba.prueba_tecnica.domain.usecase.interfaces.AlbumUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/album")
public class AlbumController {
    private static final String DELETED_SUCCESS = "Album deleted successfully";
    private static final String UPDATED_SUCCESS = "Album updated successfully";
    private static final String ADDED_SUCCESS = "Album added successfully";
    private static final String ALBUM = "Album";
    private final GeneralResponse<Album> generalResponse;
    private final GeneralResponse<Boolean> responseDelete;
    private final AlbumUseCase albumUseCase;

    @PostMapping("/create")
    public Mono<ResponseEntity<GeneralResponse<Album>>> createAlbum(@Valid @RequestBody Album album){
        return albumUseCase.createAlbum(album)
                .map(album1 -> {
                    HashMap<String, Album> data = new HashMap<>();
                    data.put(ALBUM, album1);
                    return generalResponse.generateGeneralResponseSuccess(Boolean.TRUE, ADDED_SUCCESS, data, HttpStatus.CREATED);
                })
                .onErrorResume(throwable -> Mono.just(generalResponse.generateGeneralResponseError(Boolean.FALSE, throwable.getMessage(), HttpStatus.BAD_REQUEST)));
    }
    @PutMapping("/update")
    public Mono<ResponseEntity<GeneralResponse<Album>>> updateAlbum(@Valid @RequestBody Album album){
        return albumUseCase.updateAlbum(album)
                .map(album1 -> {
                    HashMap<String, Album> data = new HashMap<>();
                    data.put(ALBUM, album1);
                    return generalResponse.generateGeneralResponseSuccess(Boolean.TRUE, UPDATED_SUCCESS, data, HttpStatus.ACCEPTED);
                })
                .onErrorResume(throwable -> Mono.just(generalResponse.generateGeneralResponseError(Boolean.FALSE, throwable.getMessage(), HttpStatus.NOT_FOUND)));
    }

    @DeleteMapping("/delete/{idAlbum}/{idUser}")
    public Mono<ResponseEntity<GeneralResponse<Boolean>>> deleteAlbum(@PathVariable Long idAlbum,@PathVariable Long idUser){
        return albumUseCase.deleteAlbum(idAlbum,idUser)
                .map(photo1 -> {
                    HashMap<String, Boolean> data = new HashMap<>();
                    data.put(ALBUM, Boolean.TRUE);
                    return responseDelete.generateGeneralResponseSuccess(Boolean.TRUE, DELETED_SUCCESS, data, HttpStatus.ACCEPTED);
                })
                .onErrorResume(throwable -> Mono.just(responseDelete.generateGeneralResponseError(Boolean.FALSE, throwable.getMessage(), HttpStatus.NOT_FOUND)));
    }

    @GetMapping
    public Flux<Album> getAllAlbums() {
        return albumUseCase.getAllAlbums();
    }


    @GetMapping("/{userId}")
    public Flux<Album> getAllAlbumsByUserId(@PathVariable Long userId) {
        return albumUseCase.getAlbumsByUserId(userId);
    }
}

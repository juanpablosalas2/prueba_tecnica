package com.prueba.prueba_tecnica.infraestructure.entry_points.reactive_web;


import com.prueba.prueba_tecnica.domain.model.GeneralResponse;
import com.prueba.prueba_tecnica.domain.model.Photo;
import com.prueba.prueba_tecnica.domain.usecase.interfaces.PhotoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequestMapping("photo")
@RequiredArgsConstructor
public class PhotoController {

    private static final String DELETED_SUCCESS = "Photo deleted successfully";
    private static final String UPDATED_SUCCESS = "Photo updated successfully";
    private static final String ADDED_SUCCESS = "Photo added successfully";
    private static final String PHOTO = "Photo";
    private final GeneralResponse<Photo> generalResponse;
    private final GeneralResponse<Boolean> responseDelete;
    private final PhotoUseCase photoUseCase;

    @PostMapping("/create/{idUser}")
    public Mono<ResponseEntity<GeneralResponse<Photo>>> createPhoto(@Valid @RequestBody Photo photo, @PathVariable Long idUser){
        return photoUseCase.createPhoto(photo,idUser)
                .map(photo1 -> {
                    HashMap<String, Photo> data = new HashMap<>();
                    data.put(PHOTO, photo1);
                    return generalResponse.generateGeneralResponseSuccess(Boolean.TRUE, ADDED_SUCCESS, data, HttpStatus.CREATED);
                })
                .onErrorResume(throwable -> Mono.just(generalResponse.generateGeneralResponseError(Boolean.FALSE, throwable.getMessage(), HttpStatus.NOT_FOUND)));
    }
    @PutMapping("/update/{idUser}")
    public Mono<ResponseEntity<GeneralResponse<Photo>>> updatePhoto(@Valid @RequestBody Photo photo, @PathVariable Long idUser){
        return photoUseCase.updatePhoto(photo,idUser)
                .map(photo1 -> {
                    HashMap<String, Photo> data = new HashMap<>();
                    data.put(PHOTO, photo1);
                    return generalResponse.generateGeneralResponseSuccess(Boolean.TRUE, UPDATED_SUCCESS, data, HttpStatus.ACCEPTED);
                })
                .onErrorResume(throwable -> Mono.just(generalResponse.generateGeneralResponseError(Boolean.FALSE, throwable.getMessage(), HttpStatus.NOT_FOUND)));
    }

    @DeleteMapping("/delete/{idUser}/{idPhoto}")
    public Mono<ResponseEntity<GeneralResponse<Boolean>>> deletePhoto(@PathVariable Long idUser,@PathVariable Long idPhoto){
        return photoUseCase.deletePhoto(idPhoto,idUser)
                .map(photo1 -> {
                    HashMap<String, Boolean> data = new HashMap<>();
                    data.put(PHOTO, photo1);
                    return responseDelete.generateGeneralResponseSuccess(Boolean.TRUE, DELETED_SUCCESS, data, HttpStatus.ACCEPTED);
                })
                .onErrorResume(throwable -> Mono.just(responseDelete.generateGeneralResponseError(Boolean.FALSE, throwable.getMessage(), HttpStatus.NOT_FOUND)));
    }

    @GetMapping
    public Flux<Photo> getAllExperiences() {
        return photoUseCase.findAllPhotos();
    }

    @GetMapping("/find/{idUser}")
    public Flux<Photo> getAllExperiencesByUser(@PathVariable Long idUser){
        return photoUseCase.findAllPhotosByUser(idUser);
    }
 }

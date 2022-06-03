package com.prueba.prueba_tecnica.infraestructure.entry_points.reactive_web;

import com.prueba.prueba_tecnica.domain.model.GeneralResponse;
import com.prueba.prueba_tecnica.domain.model.Permission;
import com.prueba.prueba_tecnica.domain.usecase.interfaces.GetAllUsersOfAlbumAccordingToPermission;
import com.prueba.prueba_tecnica.domain.usecase.interfaces.PermissionUseCase;
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
@RequestMapping("permission")
public class PermissionController {
    private static final String DELETED_SUCCESS = "Permission deleted successfully";
    private static final String UPDATED_SUCCESS = "Permission updated successfully";
    private static final String ADDED_SUCCESS = "Permission added successfully";
    private static final String PERMISSION = "Permission";
    private final GeneralResponse<Permission> generalResponse;
    private final GeneralResponse<Boolean> responseDelete;
    private final PermissionUseCase permissionUseCase;
    private final GetAllUsersOfAlbumAccordingToPermission getAllUsersOfAlbumAccordingToPermission;


    @PostMapping("/create")
    public Mono<ResponseEntity<GeneralResponse<Permission>>> createPermission(@Valid @RequestBody Permission permission){
        return permissionUseCase.addPermissionOfAlbumToUser(permission)
                .map(permission1 -> {
                    HashMap<String, Permission> data = new HashMap<>();
                    data.put(PERMISSION, permission1);
                    return generalResponse.generateGeneralResponseSuccess(Boolean.TRUE, ADDED_SUCCESS, data, HttpStatus.CREATED);
                })
                .onErrorResume(throwable -> Mono.just(generalResponse.generateGeneralResponseError(Boolean.FALSE, throwable.getMessage(), HttpStatus.NOT_FOUND)));
    }
    @PutMapping("/update")
    public Mono<ResponseEntity<GeneralResponse<Permission>>> updatePermission(@Valid @RequestBody Permission user){
        return permissionUseCase.changePermissionOfUserInAlbum(user)
                .map(permission -> {
                    HashMap<String, Permission> data = new HashMap<>();
                    data.put(PERMISSION, permission);
                    return generalResponse.generateGeneralResponseSuccess(Boolean.TRUE, UPDATED_SUCCESS, data, HttpStatus.ACCEPTED);
                })
                .onErrorResume(throwable -> Mono.just(generalResponse.generateGeneralResponseError(Boolean.FALSE, throwable.getMessage(), HttpStatus.NOT_FOUND)));
    }

    @DeleteMapping("/delete/{idPermission}")
    public Mono<ResponseEntity<GeneralResponse<Boolean>>> deletePermission(@PathVariable Long idPermission){
        return permissionUseCase.deletePermission(idPermission)
                .map(aBoolean -> {
                    HashMap<String, Boolean> data = new HashMap<>();
                    data.put(PERMISSION, Boolean.TRUE);
                    return responseDelete.generateGeneralResponseSuccess(Boolean.TRUE, DELETED_SUCCESS, data, HttpStatus.ACCEPTED);
                })
                .onErrorResume(throwable -> Mono.just(responseDelete.generateGeneralResponseError(Boolean.FALSE, throwable.getMessage(), HttpStatus.NOT_FOUND)));
    }

    @GetMapping("/{albumId}/{permission}")
    public Flux<Permission> getAllUsersOfAlbumAccordingToPermission(@PathVariable String permission,@PathVariable Long albumId){
        return getAllUsersOfAlbumAccordingToPermission.process(permission,albumId);
    }
}

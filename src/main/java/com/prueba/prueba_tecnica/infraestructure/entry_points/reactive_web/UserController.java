package com.prueba.prueba_tecnica.infraestructure.entry_points.reactive_web;

import com.prueba.prueba_tecnica.domain.model.GeneralResponse;
import com.prueba.prueba_tecnica.domain.model.Photo;
import com.prueba.prueba_tecnica.domain.model.User;
import com.prueba.prueba_tecnica.domain.model.exceptions.UserException;
import com.prueba.prueba_tecnica.domain.usecase.interfaces.UserUseCase;
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
@RequestMapping("user")
public class UserController {

    private static final String DELETED_SUCCESS = "User deleted successfully";
    private static final String UPDATED_SUCCESS = "User updated successfully";
    private static final String ADDED_SUCCESS = "User added successfully";
    private static final String USER = "USER";
    private final GeneralResponse<User> generalResponse;
    private final GeneralResponse<Boolean> responseDelete;
    private final UserUseCase userUseCase;

    @PostMapping("/create")
    public Mono<ResponseEntity<GeneralResponse<User>>> createUser(@Valid @RequestBody User user){
        return userUseCase.createUser(user)
                .map(user1 -> {
                    HashMap<String, User> data = new HashMap<>();
                    data.put(USER, user1);
                    return generalResponse.generateGeneralResponseSuccess(Boolean.TRUE, ADDED_SUCCESS, data, HttpStatus.CREATED);
                })
                .onErrorResume(throwable -> Mono.just(generalResponse.generateGeneralResponseError(Boolean.FALSE, throwable.getMessage(), HttpStatus.NOT_FOUND)));
    }
    @PutMapping("/update")
    public Mono<ResponseEntity<GeneralResponse<User>>> updateUser(@Valid @RequestBody User user){
        return userUseCase.updateUser(user)
                .map(user1 -> {
                    HashMap<String, User> data = new HashMap<>();
                    data.put(USER, user1);
                    return generalResponse.generateGeneralResponseSuccess(Boolean.TRUE, UPDATED_SUCCESS, data, HttpStatus.ACCEPTED);
                })
                .onErrorResume(throwable -> Mono.just(generalResponse.generateGeneralResponseError(Boolean.FALSE, throwable.getMessage(), HttpStatus.NOT_FOUND)));
    }

    @DeleteMapping("/delete/{idUser}")
    public Mono<ResponseEntity<GeneralResponse<Boolean>>> deleteUser(@PathVariable Long idUser){
        return userUseCase.deleteUser(idUser)
                .map(aBoolean -> {
                    HashMap<String, Boolean> data = new HashMap<>();
                    data.put(USER, Boolean.TRUE);
                    return responseDelete.generateGeneralResponseSuccess(Boolean.TRUE, DELETED_SUCCESS, data, HttpStatus.ACCEPTED);
                })
                .onErrorResume(throwable -> Mono.just(responseDelete.generateGeneralResponseError(Boolean.FALSE, throwable.getMessage(), HttpStatus.NOT_FOUND)));
    }


}

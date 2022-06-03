package com.prueba.prueba_tecnica.domain.usecase.interfaces;

import com.prueba.prueba_tecnica.domain.model.User;
import reactor.core.publisher.Mono;

public interface UserUseCase {

    Mono<User> createUser(User user);

    Mono<User> updateUser(User user);

    Mono<Void> deleteUser(Long idUser);

}

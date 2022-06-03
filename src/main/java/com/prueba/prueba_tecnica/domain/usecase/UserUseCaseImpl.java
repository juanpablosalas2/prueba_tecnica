package com.prueba.prueba_tecnica.domain.usecase;

import com.prueba.prueba_tecnica.domain.model.User;
import com.prueba.prueba_tecnica.domain.model.exceptions.PhotoException;
import com.prueba.prueba_tecnica.domain.model.exceptions.UserException;
import com.prueba.prueba_tecnica.domain.usecase.interfaces.UserUseCase;
import com.prueba.prueba_tecnica.infraestructure.driven_adapters.repositories.UserRepository;
import com.prueba.prueba_tecnica.infraestructure.driven_adapters.repositories.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserUseCaseImpl implements UserUseCase {


    private static final String ID_NOT_FOUND = "User With ID %d not found";
    private final UserRepository repository;


    @Override
    public Mono<User> createUser(User user) {
        return Mono.just(user)
                .map(UserEntity::convertToEntity)
                .map(repository::save)
                .map(UserEntity::convertToModel)
                .onErrorResume(throwable -> Mono.error(new UserException("User Could not be saved ")));
    }

    @Override
    public Mono<User> updateUser(User user) {
        return Mono.just(user)
                .map(UserEntity::convertToEntity)
                .map(user1 -> repository.findById(user1.getId()))
                .switchIfEmpty(Mono.error(new UserException(String.format(ID_NOT_FOUND, user.getId()))))
                .map(userEntity -> {
                    UserEntity newUser = UserEntity.builder()
                            .id(userEntity.get().getId()).name(user.getName())
                            .email(user.getEmail()).website(user.getWebsite())
                            .phone(user.getPhone())
                            .build();

                    return repository.save(newUser);
                })
                .map(UserEntity::convertToModel)
                .onErrorResume(throwable -> Mono.error(new UserException("User Could not be Update ")));
    }

    @Override
    public Mono<Void> deleteUser(Long idUser) {
        return Mono.just(idUser)
                .map(repository::existsById)
                .switchIfEmpty(Mono.error(new UserException(String.format(ID_NOT_FOUND, idUser))))
                .map(exist -> Boolean.TRUE.equals(exist) ? idUser : null)
                .flatMap(aLong -> {
                    repository.deleteById(aLong);
                    return Mono.when();
                })
                .onErrorResume(throwable -> Mono.error(new PhotoException("User could not be deleted", throwable)));
    }

}
